package fr.iut.referendum;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.transform.Source;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

public class Admin {
    private String login;
    private String password;

    public Admin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void run(String hostname, int port) {
        try {
            // Configuration SSL
            System.setProperty("javax.net.ssl.trustStore", "keystore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "Admin!123");

            // Création d'une socket sécurisée
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            try (SSLSocket socket = (SSLSocket) socketFactory.createSocket(hostname, port);
                 OutputStream output = socket.getOutputStream();
                 PrintWriter writer = new PrintWriter(output, true);
                 InputStream input = socket.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

                System.out.println("Pour obtenir les informations des refrendum, tapez info");
                System.out.println("Pour creer un referundum, tapez new");
                System.out.println("Pour quitter, tapez exit");

                Scanner clavier = new Scanner(System.in);
                boolean running = true;
                while (running) {
                    String commande = clavier.nextLine();
                    if (commande.equals("exit")) {
                        running = exit(writer, reader);
                    } else if (commande.equals("info")) {
                        infoReferendum(writer, reader);
                    } else if (commande.equals("new")) {
                        newReferendum(writer, clavier, reader);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static boolean exit(PrintWriter writer, BufferedReader reader) throws IOException {
        boolean running;
        System.out.println("Fermeture de la connexion.");
        writer.println("EXIT");
        // Attendre une confirmation de déconnexion
        String response = reader.readLine();
        if (response != null && response.equals("1")) {
            System.out.println("Déconnecté du serveur.");
        }
        running = false;
        return running;
    }

    public static void infoReferendum(PrintWriter writer, BufferedReader reader) throws IOException {
        writer.println("GET_SERVER_INFO");
        String response;
        while (!(response = reader.readLine()).isEmpty()) {
            System.out.println("Server response: " + response);
        }
    }

    public static void newReferendum(PrintWriter writer, Scanner clavier, BufferedReader reader) throws IOException {
        writer.println("NEW_REFERENDUM");

        System.out.println("Nom du referendum : ");
        String nom = clavier.nextLine();
        while (nom.isEmpty()) {
            System.out.println("Nom invalide");
            nom = clavier.nextLine();
        }
        writer.println(nom);

        System.out.println("Date de fin : ");
        envoyeDate(writer, clavier);

        System.out.println("Server response: " + reader.readLine());
    }

    public static void envoyeDate(PrintWriter writer, Scanner clavier) {
        System.out.println("Annee : ");
        String annee = clavier.nextLine();
        while (!annee.matches("[0-9]+") || Integer.parseInt(annee) < 2000 || Integer.parseInt(annee) > 2100) {
            System.out.println("Choix invalide");
            annee = clavier.nextLine();
        }
        writer.println(annee);

        System.out.println("Mois : ");
        String mois = clavier.nextLine();
        while (!mois.matches("[0-9]+") || Integer.parseInt(mois) < 1 || Integer.parseInt(mois) > 12) {
            System.out.println("Choix invalide");
            mois = clavier.nextLine();
        }
        writer.println(mois);

        System.out.println("Jour : ");
        String jour = clavier.nextLine();
        while (!jour.matches("[0-9]+") || Integer.parseInt(jour) < 1 || Integer.parseInt(jour) > getMaxDaysInMonth(Integer.parseInt(annee), Integer.parseInt(mois))) {
            System.out.println("Choix invalide");
            jour = clavier.nextLine();
        }
        writer.println(jour);

        System.out.println("Heure : ");
        String heure = clavier.nextLine();
        while (!heure.matches("[0-9]+") || Integer.parseInt(heure) < 0 || Integer.parseInt(heure) > 23) {
            System.out.println("Choix invalide");
            heure = clavier.nextLine();
        }
        writer.println(heure);
    }

    public static int getMaxDaysInMonth(int year, int month) {
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    return 29; // année bisextile
                } else {
                    return 28;
                }
            default:
                return 31;
        }
    }
}
