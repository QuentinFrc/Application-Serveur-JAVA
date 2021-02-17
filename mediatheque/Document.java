package mediatheque;

import exception.EmpruntException;
import exception.ReservationException;
import ressources.Abonne;

public interface Document {
		int numero();
		void reservationPour(Abonne ab) throws ReservationException;
		void empruntPar(Abonne ab) throws EmpruntException;
		// retour document ou annulation réservation
		void retour();
}