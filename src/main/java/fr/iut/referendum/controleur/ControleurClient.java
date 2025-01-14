package fr.iut.referendum.controleur;

import fr.iut.referendum.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class ControleurClient {

    @FXML
    private ListView<String> listViewReferendums;

    @FXML
    private Button buttonSelect;

    @FXML
    private Label labelStatus;

    @FXML
    private RadioButton radioOui, radioNon;

    private ToggleGroup toggleGroup;

    private Client client;
    private PrintWriter writer;
    private BufferedReader reader;

    public void setCommunication(PrintWriter writer, BufferedReader reader, Client client) {
        this.writer = writer;
        this.reader = reader;
        this.client = client;
    }

    @FXML
    public void initialize() {
        toggleGroup = new ToggleGroup();
        radioOui.setToggleGroup(toggleGroup);
        radioNon.setToggleGroup(toggleGroup);
    }

    private void loadReferendums() {
        try {
            writer.println("GET_SERVER_INFO");
            String response;
            while (!(response = reader.readLine()).equals("fin")) {
                listViewReferendums.getItems().add(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            labelStatus.setText("Erreur de chargement des référendums.");
        }
    }

    @FXML
    private void handleSelection(ActionEvent event) {
        loadReferendums();
        String selectedReferendum = listViewReferendums.getSelectionModel().getSelectedItem();
        if (selectedReferendum == null) {
            labelStatus.setText("Veuillez sélectionner un référendum.");
            return;
        }

        if (toggleGroup.getSelectedToggle() == null) {
            labelStatus.setText("Veuillez sélectionner une option (Oui ou Non).");
            return;
        }

        int idReferendum = Integer.parseInt(selectedReferendum.split(" - ")[0]);
        boolean choix = radioOui.isSelected();

        if (!client.voterReferendum(writer, reader, idReferendum, choix)) {
            labelStatus.setText("Erreur lors de l'envoi du vote");
        }
        else {
            labelStatus.setText("Vote enregistré.");
        }
//        try {
//            writer.println("VOTER_REFERENDUM");
//            writer.println(idReferendum);
//            String response = reader.readLine();
//            if ("Ok".equals(response)) {
//                // Receive public key
//                BigInteger p = new BigInteger(reader.readLine());
//                BigInteger g = new BigInteger(reader.readLine());
//                BigInteger h = new BigInteger(reader.readLine());
//                BigInteger[] pk = new BigInteger[]{p, g, h};
//
//                // Get vote choice
//                String choix = radioOui.isSelected() ? "Oui" : "Non";
//                BigInteger choixint = "Oui".equals(choix) ? BigInteger.ONE : BigInteger.ZERO;
//
//                // Encrypt choice
//                BigInteger[] choixCrypter = Crypto.encrypt(choixint, pk);
//
//                // Send encrypted vote
//                writer.println(choixCrypter[0]);
//                writer.println(choixCrypter[1]);
//
//                labelStatus.setText("Vote enregistré: " + reader.readLine());
//            } else {
//                labelStatus.setText("Erreur de sélection du référendum.");
//            }
//        } catch (Exception e) {
//            labelStatus.setText("Erreur de liaison avec le serveur");
//            throw new RuntimeException(e);
//        }
    }
}