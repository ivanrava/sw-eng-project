package it.unibs.ing.ingsw.article;

import it.unibs.ing.ingsw.auth.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

public class Exchange implements Serializable {
    private final Article articleProposed;
    private final Article articleWanted;
    private LocalDate whenLastEvent;
    private String proposedWhere;
    private LocalDateTime proposedWhen;
    private User to;

    /**
     * Costruttore iniziale per la creazione dell'offerta
     * @param articleProposed Articolo proposto per lo scambio
     * @param articleWanted Articolo desiderato dallo scambio
     */
    public Exchange(Article articleProposed, Article articleWanted) {
        this.articleProposed = articleProposed;
        articleProposed.setState(ArticleState.OFFERTA_ACCOPPIATA);
        this.articleWanted = articleWanted;
        articleWanted.setState(ArticleState.OFFERTA_SELEZIONATA);
        this.whenLastEvent = LocalDate.now();
        this.to = articleWanted.getOwner();
    }

    private LocalDate getWhenLastEvent() {
        return whenLastEvent;
    }

    /**
     * Controlla se lo scambio è scaduto
     * @param deadline Scadenza da considerare
     * @param unit Unità temporale da considerare
     * @return 'true' se scaduto, 'false' altrimenti
     */
    private boolean isExpired(int deadline, TemporalUnit unit) {
        return getWhenLastEvent().plus(deadline, unit).isBefore(LocalDate.now());
    }

    /**
     * Controlla se lo scambio è scaduto.
     * Se sì, resetta gli stati delle offerte e annulla lo scambio.
     * @param deadLine Scadenza da usare per la verifica
     * @param unit Unità temporale
     * @return 'true' se scaduto, 'false' altrimenti
     */
    public boolean ifExpiredReset(int deadLine, TemporalUnit unit) {
        if (isExpired(deadLine, unit)){
            resetOffers();
            return true;
        }
        return false;
    }

    /**
     * Accetta l'offerta, da decidere dove e quando
     */
    public void acceptProposal() {
        articleProposed.setState(ArticleState.OFFERTA_SCAMBIO);
        articleWanted.setState(ArticleState.OFFERTA_SCAMBIO);
        whenLastEvent = LocalDate.now();
    }

    /**
     * Accetta lo scambio
     */
    public void acceptExchange() {
        articleProposed.setState(ArticleState.OFFERTA_CHIUSA);
        articleWanted.setState(ArticleState.OFFERTA_CHIUSA);
    }

    /**
     * Resetta gli stati degli articoli
     */
    public void resetOffers() {
        articleProposed.setState(ArticleState.OFFERTA_APERTA);
        articleWanted.setState(ArticleState.OFFERTA_APERTA);
    }

    /**
     * Aggiorna la proposta spaziotemporale di scambio
     * @param where Dove
     * @param when Quando
     */
    public void updateProposal(String where, LocalDateTime when) {
        this.proposedWhere = where;
        this.proposedWhen = when;
        switchUser();
        whenLastEvent = LocalDate.now();
    }

    /**
     * Cambia il destinatario, selezionando l'altro utente coinvolto
     */
    private void switchUser() {
        this.to = to.equals(articleProposed.getOwner()) ? articleWanted.getOwner() : articleProposed.getOwner();
    }

    /**
     * Ritorna se gli articoli sono "in scambio"
     * @return 'true' se si stanno scambiando, 'false' altrimenti
     */
    public boolean areExchanging() {
        return articleWanted.getState().equals(ArticleState.OFFERTA_SCAMBIO) && articleProposed.getState().equals(ArticleState.OFFERTA_SCAMBIO);
    }

    @Override
    public String toString() {
        String whereAndWhen = areExchanging() ? String.format(" (a %s in data %s)", proposedWhere, proposedWhen) : "";
        return String.format("Il %s qualcuno ha proposto lo scambio" + whereAndWhen + ":\n\t%s\n\t%s\n",
                whenLastEvent,
                articleWanted.toString(),
                articleProposed.toString());
    }

    /**
     * Dice se l'utente passato è coinvolto nello scambio
     * @param user L'utente da controllare
     * @return 'true' se è coinvolto, 'false' altrimenti
     */
    public boolean concerns(User user) {
        return articleProposed.getOwner().equals(user) || articleWanted.getOwner().equals(user);
    }

    /**
     * Ritorna se gli articoli sono stati solo selezionati
     * @return 'true' se solo selezionati, 'false' altrimenti
     */
    public boolean areOnlyPaired() {
        return articleWanted.getState().equals(ArticleState.OFFERTA_SELEZIONATA) && articleProposed.getState().equals(ArticleState.OFFERTA_ACCOPPIATA);
    }

    /**
     * Controlla se lo scambio aspetta risposta dall'utente passato
     * @param user Utente da controllare
     * @return 'true' se aspetta una risposta dall'utente passato, 'false' altrimenti
     */
    public boolean awaitsAnswerFrom(User user) {
        return to.getUsername().equals(user.getUsername());
    }
}
