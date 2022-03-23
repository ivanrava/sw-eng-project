package it.unibs.ing.ingsw.article;

import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.io.Saves;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExchangeController {
    private final List<Exchange> exchangeList = new ArrayList<>();
    private final ArticleController articleController;

    public ExchangeController(Saves saves) {
        articleController = new ArticleController(saves);
    }

    /**
     * Ritorna una lista di rappresentazioni degli articoli "in scambio" per un utente passato
     * @param user L'utente di cui ritornare gli articoli "in scambio"
     * @return La lista delle rappresentazioni degli articoli "in scambio" per quell'utente
     */
    public List<String> getExchangesString(User user) {
        return exchangeList.stream()
                .filter(exchange -> exchange.concerns(user))
                .filter(Exchange::areExchanging)
                .map(Exchange::toString)
                .toList();
    }

    public List<Exchange> getExchanges(User user) {
        return exchangeList.stream()
                .filter(exchange -> exchange.concerns(user))
                .filter(Exchange::areExchanging)
                .toList();
    }

    /**
     * Ritorna una lista di rappresentazioni dei baratti tra articoli "accoppiati / scambiati"
     * @param user L'utente di cui ritornare gli articoli "accoppiati / scambiati"
     * @return La lista delle rappresentazioni degli articoli "accoppiati / scambiati" per quell'utente
     */
    public List<String> getProposalsString(User user) {
        return exchangeList.stream()
                .filter(exchange -> exchange.concerns(user))
                .filter(Exchange::areOnlyPaired)
                .map(Exchange::toString)
                .toList();
    }

    public List<Exchange> getProposals(User user) {
        return exchangeList.stream()
                .filter(exchange -> exchange.concerns(user))
                .filter(Exchange::areOnlyPaired)
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
        assert proposed != null : "L'articolo proposto non esiste!";
        assert wanted != null : "L'articolo desiderato non esiste!";
        exchangeList.add(new Exchange(proposed, wanted));
    }

    private List<Exchange> getExchangesByUser (User user){  //FIXME
        return  (List<Exchange>) exchangeList.stream()
                .filter(exchange -> exchange.concerns(user))
                .filter(Exchange::areExchanging);
    }

    public void updateProposal(String proposedWhere, LocalDateTime proposedWhen, User user, int indexOfExchange){ //FIXME indexOfExchange decidere come passare la selezione del baratto
        List<Exchange> exchangeUserList = getExchangesByUser(user);
        Exchange exchangeToSet = exchangeUserList.get(indexOfExchange);
        exchangeToSet.updateProposal(proposedWhere, proposedWhen);
    }

    public User getToUser(User user, int indexOfExchange){
        List<Exchange> exchangeUserList = getExchangesByUser(user);
        Exchange exchange = exchangeUserList.get(indexOfExchange);
        return exchange.getTo();
    }


    public void acceptProposal(int indexOfExchange, User user){
        List<Exchange> exchangeUserList = getExchangesByUser(user);
        Exchange exchangeAccepted = exchangeUserList.get(indexOfExchange);
        exchangeAccepted.acceptExchange();
    }
}
