package it.unibs.ing.ingsw.article;

import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.config.ConfigController;
import it.unibs.ing.ingsw.io.DataContainer;
import it.unibs.ing.ingsw.io.Saves;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ExchangeController {
    private static final String ASSERTION_ARTICLE_PROPOSED_NOT_EXISTS = "L'articolo proposto non esiste!";
    private static final String ASSERTION_ARTICLE_WANTED_NOT_EXISTS = "L'articolo desiderato non esiste!";
    private final List<Exchange> exchangeList;
    private final ArticleController articleController;
    private final ConfigController configController;

    public ExchangeController(DataContainer saves) {
        articleController = new ArticleController(saves);
        configController = new ConfigController(saves);
        exchangeList = saves.getExchanges();
    }

    /**
     * Cancella gli scambi scaduti
     */
    public void deleteExpiredExchanges() {
        int deadLine = configController.getDeadline();
        exchangeList.removeIf(exchange -> exchange.ifExpiredReset(deadLine, ChronoUnit.DAYS));
    }

    /**
     * Ritorna i baratti "in scambio" che riguardano un certo utente
     * @param user Utente che li riguarda
     * @return Baratti "in scambio" per quell'utente
     */
    public List<Exchange> getExchangingExchanges(User user) {
        return exchangeList.stream()
                .filter(exchange -> exchange.concerns(user))
                .filter(Exchange::areExchanging)
                .toList();
    }


    /**
     * Ritorna i baratti "che aspettano conferma" dall'utente passato
     * @param user Utente che deve confermare la proposta
     * @return Baratti "che aspettano conferma" dall'utente passato
     */
    public List<Exchange> getProposalsForUser(User user) {
        return exchangeList.stream()
                .filter(exchange -> exchange.awaitsAnswerFrom(user) && exchange.areOnlyPaired())
                .toList();
    }

    /**
     * Inizia uno scambio tra 2 articoli
     * @param articleProposedId Id dell'articolo proposto
     * @param articleWantedId Id dell'articolo desiderato
     */
    public void startExchange(int articleProposedId, int articleWantedId) {
        Article proposed = articleController.getArticle(articleProposedId);
        Article wanted = articleController.getArticle(articleWantedId);
        assert proposed != null : ASSERTION_ARTICLE_PROPOSED_NOT_EXISTS;
        assert wanted != null : ASSERTION_ARTICLE_WANTED_NOT_EXISTS;
        exchangeList.add(new Exchange(proposed, wanted));
    }

    public void updateAppointment(String proposedWhere, LocalDateTime proposedWhen, Exchange exchange){
        exchange.updateProposal(proposedWhere, proposedWhen);
    }

    /**
     * Accetta il baratto definitivamente (con luogo e tempo)
     * @param exchange Baratto da accettare
     */
    public void acceptExchange(Exchange exchange) {
        exchange.acceptExchange();
    }

    /**
     * Ritorna le proposte di baratto che aspettano una risposta dall'utente dato
     * @param user Utente dato
     * @return Proposte di baratto che aspettano l'utente
     */
    public List<Exchange> getExchangesAwaitingForAnswer(User user) {
        return exchangeList.stream()
                .filter(exchange -> exchange.awaitsAnswerFrom(user))
                .filter(Exchange::areExchanging)
                .toList();
    }

    /**
     * Accetta la proposta di baratto
     * @param exchange Baratto di cui accettare la proposta
     */
    public void acceptProposal(Exchange exchange) {
        exchange.acceptProposal();
    }

    /**
     * Rifiuta la proposta, e annulla il baratto
     * @param exchange Scambio da rifiutare
     */
    public void rejectProposal(Exchange exchange) {
        exchange.resetOffers();
        exchangeList.remove(exchange);
    }
}
