package it.unibs.ing.ingsw.ui.controllers;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.domain.auth.User;
import it.unibs.ing.ingsw.domain.config.ConfigController;
import it.unibs.ing.ingsw.domain.business.Article;
import it.unibs.ing.ingsw.domain.business.Exchange;
import it.unibs.ing.ingsw.domain.business.ExchangeController;
import it.unibs.ing.ingsw.services.persistence.DataContainer;
import it.unibs.ing.ingsw.ui.views.AbstractView;
import it.unibs.ing.ingsw.ui.views.ExchangeView;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

public class ExchangeMVController extends AbstractMVController {
    private static final String INPUT_WHERE_WHEN = "Ok, ora proponi un luogo / ora per lo scambio...";

    private static final String ERROR_UNEXISTANT_CONFIGURATION = "Configurazione inesistente :-(";
    private static final String ERROR_NO_OPEN_ARTICLES = "Non hai articoli da scambiare :(";
    private static final String ERROR_ADD_ARTICLE_BEFORE_PROPOSAL = "Aggiungi un articolo prima di proporre uno scambio";
    private static final String ERROR_NO_MATCHING_ARTICLES = "Non ci sono articoli con cui fare lo scambio :(";
    private static final String ERROR_NO_PROPOSALS = "Non hai proposte da gestire :-(";
    private static final String ERROR_NO_DISCUSSIONS = "Non hai nessuna proposta da vagliare :(";

    private static final String PROPOSE_EXCHANGE = "Proponi un baratto";
    private static final String MANAGE_EXCHANGES = "Gestisci i baratti che ti sono stati proposti";
    private static final String SHOW_ARTICLES_ON_EXCHANGE = "Mostra i tuoi articoli in scambio (e ultime risposte)";
    private static final String ACCEPT_MODIFY_WHERE_WHEN_EXCHANGE = "Accetta/modifica luogo/tempo dei baratti";
    private final ExchangeController exchangeController;
    private final ConfigController configController;
    private final ExchangeView exchangeView;
    private final ArticleMVController articleMVController;

    public ExchangeMVController(DataContainer saves, InputProvider inputProvider, Clock clock) {
        exchangeController = new ExchangeController(saves, clock);
        configController = new ConfigController(saves);
        exchangeView = new ExchangeView(inputProvider);
        articleMVController = new ArticleMVController(saves, inputProvider);
    }

    @Override
    protected boolean beforeExecute() {
        if (!configController.existsDefaultValues()) {
            System.out.println(ERROR_UNEXISTANT_CONFIGURATION);
            return false;
        }
        //controllo sugli scambi in scadenza
        exchangeController.deleteExpiredExchanges();
        return true;
    }

    @Override
    protected LinkedHashMap<String, Runnable> getMenuOptions(User user) {
        LinkedHashMap<String, Runnable> menuOptions = new LinkedHashMap<>();
        menuOptions.put(PROPOSE_EXCHANGE, () -> proposeExchange(user));
        menuOptions.put(MANAGE_EXCHANGES, () -> manageProposals(user));
        menuOptions.put(SHOW_ARTICLES_ON_EXCHANGE, () -> exchangeView.printExchangingArticles(exchangeController.getExchangingExchanges(user)));
        menuOptions.put(ACCEPT_MODIFY_WHERE_WHEN_EXCHANGE, () -> manageAppointments(user));
        return menuOptions;
    }

    @Override
    protected AbstractView getView() {
        return exchangeView;
    }

    /**
     * Propone uno scambio, selezionando articolo proprio e altrui di ugual categoria
     *
     * @param user Utente che propone lo scambio
     */
    private void proposeExchange(User user) {
        // Seleziona la proposta
        Article articleProposed = articleMVController.selectProposal(user);
        if (articleProposed == null) {
            exchangeView.message(ERROR_NO_OPEN_ARTICLES);
            exchangeView.message(ERROR_ADD_ARTICLE_BEFORE_PROPOSAL);
            return;
        }
        // Seleziona l'articolo desiderato
        Article articleWanted = articleMVController.selectWanted(user, articleProposed);
        if (articleWanted == null) {
            exchangeView.message(ERROR_NO_MATCHING_ARTICLES);
            return;
        }
        exchangeController.startExchange(articleProposed, articleWanted);
    }

    /**
     * Chiede all'utente di gestire i baratti che gli sono stati proposti
     *
     * @param user Utente di cui chiedere la proposta
     */
    private void manageProposals(User user) {
        List<Exchange> proposalsForUser = exchangeController.getProposalsForUser(user);
        if (proposalsForUser.isEmpty()) {
            exchangeView.message(ERROR_NO_PROPOSALS);
            return;
        }
        Exchange selectedExchange = exchangeView.selectOptionFromCollection(proposalsForUser);
        exchangeView.printExchange(selectedExchange);
        if (exchangeView.askAcceptExchange()) {
            exchangeController.acceptProposal(selectedExchange);
            exchangeView.message(INPUT_WHERE_WHEN);
            editAppointment(selectedExchange);
        } else {
            exchangeController.rejectProposal(selectedExchange);
        }
    }


    /**
     * Gestisce gli appuntamenti (dei baratti) di un utente
     *
     * @param user Utente di cui gestire gli appuntamenti
     */
    private void manageAppointments(User user) {
        List<Exchange> exchangeList = exchangeController.getExchangesAwaitingForAnswer(user);
        if (exchangeList.isEmpty()) {
            exchangeView.message(ERROR_NO_DISCUSSIONS);
            return;
        }
        Exchange exchange = exchangeView.selectOptionFromCollection(exchangeList);
        exchangeView.printExchange(exchange);

        if (exchangeView.askAppointmentConfirmation()) exchangeController.acceptExchange(exchange);
        else editAppointment(exchange);
    }

    /**
     * Modifica la proposta fatta
     *
     * @param exchange Scambio di cui modificare la proposta
     */
    private void editAppointment(Exchange exchange) {
        String proposedWhere = exchangeView.askWhere(configController.getPlaces());
        LocalDateTime proposedWhen = exchangeView.askProposedWhen(configController.getValidDaysOfWeek(), configController.getTimeIntervals());
        exchangeController.updateAppointment(proposedWhere, proposedWhen, exchange);
    }


}
