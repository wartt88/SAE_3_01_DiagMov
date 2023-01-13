package mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.Vue;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.Utils.ChargementRes;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.controller.ControllerDragClickPourClasse;
import mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.Themes.Theme;
import mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.model.Classe;

import java.util.ArrayList;

/**
 * Une vue qui permet d'afficher les informations d'une classe (nom, attributs, constructeurs et méthodes).
 */
public class VueClasse extends VBox {

    private Classe modele;
    private VBox attributs;
    private Label methodes;
    private Label constructeurs;

    public VueClasse(Classe modele) {
        this.modele = modele;
        ControllerDragClickPourClasse controller = new ControllerDragClickPourClasse(modele, this);
        setEventHandlers(controller);
        this.creerVue();
    }

    /**
     * Affiche les informations de la classe dans une boîte de type VBox.
     *
     * @return une boîte de type VBox contenant les informations de la classe
     */
    public void creerVue() {
        VBox vBoxHaut = creerVBoxHaut();

        attributs = creerVBoxAttributs();
        constructeurs = creerLabelConstructeur();
        methodes = creerLabelMethode();

        ArrayList<Node> listNode = new ArrayList<>();
        listNode.add(vBoxHaut);
        if (!modele.getModele().getEtatNav("A") && modele.isAttributActive())
            listNode.add(attributs);
        if (!modele.getModele().getEtatNav("C") && modele.isConstructeurActive() && !constructeurs.getText().equals(""))
            listNode.add(constructeurs);
        if (!modele.getModele().getEtatNav("M") && modele.isMethodesActive())
            listNode.add(methodes);
        this.getChildren().addAll(listNode);

        ContextMenu contextMenu = creerMenuContextuel();
        this.setOnContextMenuRequested(event -> contextMenu.show(this, event.getScreenX(), event.getScreenY()));

        mettreStrokeAndBackground();
        placerClasse(modele.getCoordonnesX(), modele.getCoordonnesY());

    }

    public void mettreStrokeAndBackground() {
        Theme t = modele.getTheme();

        this.setBackground(new Background(new BackgroundFill(t.getFondClasse(), null, null)));
        this.setBorder(new Border(new BorderStroke(t.getBordureEtBtnImportant(), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 2, 2, 2))));

    }

    public VBox creerVBoxHaut() {
        Theme t = modele.getTheme();

        VBox vBoxHaut = new VBox();
        vBoxHaut.setBackground(new Background(new BackgroundFill(t.getTopClasse(), null, null)));
        vBoxHaut.setBorder(new Border(new BorderStroke(t.getBordureEtBtnImportant(), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 2, 0))));
        ImageView iv;
        if (modele.isInterface()) {
            iv = new ImageView(ChargementRes.getLetterI());
        } else {
            iv = new ImageView(ChargementRes.getLetterC());
        }
        iv.setPreserveRatio(true);
        iv.setFitHeight(15);

        Label lbNom = new Label(modele.getNomClasse());
        lbNom.setFont(Font.font(lbNom.getFont().getName(), FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14));
        lbNom.setTextFill(t.getCouleurTxtCls());
        lbNom.setGraphic(iv);
        vBoxHaut.getChildren().add(lbNom);

        return vBoxHaut;
    }

    public VBox creerVBoxAttributs() {
        Theme t = modele.getTheme();

        VBox vBoxMilieu = new VBox();
        vBoxMilieu.setBorder(new Border(new BorderStroke(t.getBordureEtBtnImportant(), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 2, 0))));

        StringBuilder sba = new StringBuilder();
        for (String s : modele.getAttributs()) {
            sba.append(s + "\n");
        }
        Label lbAttributs = new Label(sba.toString());
        lbAttributs.setTextFill(t.getCouleurTxtCls());
        vBoxMilieu.getChildren().add(lbAttributs);

        return vBoxMilieu;
    }

    public Label creerLabelConstructeur() {
        Theme t = modele.getTheme();
        StringBuilder sbc = new StringBuilder();
        for (String s : modele.getConstructeurs()) {
            sbc.append(s + "\n");
        }
        Label lbConstructeurs = new Label(sbc.toString());
        lbConstructeurs.setTextFill(t.getCouleurTxtCls());
        return lbConstructeurs;
    }

    public Label creerLabelMethode() {
        Theme t = modele.getTheme();
        StringBuilder sbm = new StringBuilder();
        for (String s : modele.getMethodes()) {
            sbm.append(s + "\n");
        }
        Label lbMethodes = new Label(sbm.toString());
        lbMethodes.setTextFill(t.getCouleurTxtCls());
        return lbMethodes;
    }


    public void placerClasse(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    public Classe getModele() {
        return modele;
    }

    public int getLargeurClasse() {
        return (int) this.getWidth();
    }

    public int getHauteurClasse() {
        return (int) this.getHeight();
    }

    private ContextMenu creerMenuContextuel() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem suppression = new MenuItem("Supprimer la classe");

        MenuItem masquerAttribut = new MenuItem("masquer attribut");
        MenuItem masquerMethode = new MenuItem("masquer methode");
        MenuItem masquerConstructeur = new MenuItem("masquer constructeur");

        MenuItem afficherAttribut = new MenuItem("afficher attributs");
        MenuItem afficherMethode = new MenuItem("afficher methode");
        MenuItem afficherConstructeur = new MenuItem("afficher constructeur");

        suppression.setOnAction(actionEvent -> modele.supprimerClasseDansModele());

        masquerAttribut.setOnAction(actionEvent -> {
            cacherAttribut();
            int index = contextMenu.getItems().indexOf(masquerAttribut);
            contextMenu.getItems().remove(masquerAttribut);
            contextMenu.getItems().add(index, afficherAttribut);
        });

        masquerMethode.setOnAction(actionEvent -> {
            cacherMethodes();
            int index = contextMenu.getItems().indexOf(masquerMethode);
            contextMenu.getItems().remove(masquerMethode);
            contextMenu.getItems().add(index, afficherMethode);
        });

        masquerConstructeur.setOnAction(actionEvent -> {
            cacherConstructeurs();
            int index = contextMenu.getItems().indexOf(masquerConstructeur);
            contextMenu.getItems().remove(masquerConstructeur);
            contextMenu.getItems().add(index, afficherConstructeur);
        });

        afficherAttribut.setOnAction(actionEvent -> {
            voirAttribut();
            int index = contextMenu.getItems().indexOf(afficherAttribut);
            contextMenu.getItems().remove(afficherAttribut);
            contextMenu.getItems().add(index, masquerAttribut);
        });
        afficherMethode.setOnAction(actionEvent -> {
            voirMethode();
            int index = contextMenu.getItems().indexOf(afficherMethode);
            contextMenu.getItems().remove(afficherMethode);
            contextMenu.getItems().add(index, masquerMethode);
        });
        afficherConstructeur.setOnAction(actionEvent -> {
            voirConstructeur();
            int index = contextMenu.getItems().indexOf(afficherConstructeur);
            contextMenu.getItems().remove(afficherConstructeur);
            contextMenu.getItems().add(index, masquerConstructeur);
        });

        contextMenu.getItems().add(suppression);
        if (modele.isAttributActive()) {
            contextMenu.getItems().add(masquerAttribut);
        } else {
            contextMenu.getItems().add(afficherAttribut);
        }
        if (modele.isMethodesActive()) {
            contextMenu.getItems().add(masquerMethode);
        } else {
            contextMenu.getItems().add(afficherMethode);
        }
        if (modele.isConstructeurActive()) {
            contextMenu.getItems().add(masquerConstructeur);
        } else {
            contextMenu.getItems().add(afficherConstructeur);
        }
        return contextMenu;
    }

    private void voirAttribut() {
        this.getChildren().add(1, attributs);
        this.modele.setAttributActive(!modele.isAttributActive());
    }

    private void voirMethode() {
        int sizeVbox = this.getChildren().size();
        this.getChildren().add(sizeVbox, methodes);
        this.modele.setMethodesActive(!modele.isMethodesActive());

    }

    private void voirConstructeur() {
        int sizeVbox = this.getChildren().size();
        if (sizeVbox >= 3)
            this.getChildren().add(2, constructeurs);
        else
            this.getChildren().add(1, constructeurs);
        this.modele.setConstructeurActive(!modele.isConstructeurActive());
    }

    private void cacherAttribut() {
        this.getChildren().remove(attributs);
        this.modele.setAttributActive(!modele.isAttributActive());

    }

    private void cacherMethodes() {
        this.getChildren().remove(methodes);
        this.modele.setMethodesActive(!modele.isMethodesActive());
    }

    private void cacherConstructeurs() {
        this.getChildren().remove(constructeurs);
        this.modele.setConstructeurActive(!modele.isConstructeurActive());
    }

    /**
     * Méthode pour vérifier si le clic de souris est sur toute la bordure de l'élément.
     *
     * @param mouseEvent l'événement de clic de souris
     * @return vrai si le clic est sur toute la bordure, faux sinon
     */
    public boolean estSurBordure(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        double width = node.getBoundsInLocal().getWidth();
        double height = node.getBoundsInLocal().getHeight();

        return (x < 5 || x > width - 5 || y < 5 || y > height - 5);
    }

    /**
     * Méthode pour vérifier si le clic de souris est sur le bord inférieur et droit de l'élément.
     *
     * @param mouseEvent l'événement de clic de souris
     * @return vrai si le clic est sur le bord inférieur et droit, faux sinon
     */
    public boolean estSurBordInferieurEtDroit(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        double width = node.getBoundsInLocal().getWidth();
        double height = node.getBoundsInLocal().getHeight();

        return (x > width - 5 || y > height - 5);
    }

    /**
     * Méthode pour vérifier si le clic de souris est sur le bord gauche et haut de l'élément.
     *
     * @param mouseEvent l'événement de clic de souris
     * @return vrai si le clic est sur le bord gauche et haut, faux sinon
     */
    public boolean estSurBordGaucheEtHaut(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        return (x < 5 || y < 5);
    }

    private void setEventHandlers(ControllerDragClickPourClasse controller) {
        setOnMouseMoved(e -> handleMouseMoved(e, controller));
        setOnMousePressed(e -> handleMousePressed(e, controller));
        setOnMouseReleased(e -> handleMouseReleased(e, controller));
        setOnMouseDragged(controller);
    }

    private void handleMouseMoved(MouseEvent e, ControllerDragClickPourClasse controller) {
        this.setCursor(Cursor.OPEN_HAND);
        if (estSurBordure(e)) {
            this.setCursor(Cursor.SE_RESIZE);
        }
    }

    private void handleMousePressed(MouseEvent e, ControllerDragClickPourClasse controller) {
        if (estSurBordure(e)) {
            controller.setRedimensionnementActif(true);
            this.setCursor(Cursor.SE_RESIZE);
        } else {
            this.setCursor(Cursor.CLOSED_HAND);
        }

        controller.setxDuClique(e.getX());
        controller.setyDuClique(e.getY());
    }

    private void handleMouseReleased(MouseEvent e, ControllerDragClickPourClasse controller) {
        controller.setRedimensionnementActif(false);
        this.setCursor(Cursor.OPEN_HAND);
    }


}

