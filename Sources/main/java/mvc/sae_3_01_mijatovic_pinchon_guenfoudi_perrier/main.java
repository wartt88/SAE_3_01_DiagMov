package mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.Vue.TopBarre;
import mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.Vue.VueClasse;
import mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.Vue.VueRepCourant;
import mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.interfacesETabstract.Observateur;
import mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.model.Classe;
import mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.model.Modele;

public class main extends Application {
    public static double SIZEX = 0;
    public static  double SIZEY = 0;
    @Override
    public void start(Stage stage) throws Exception {
        Modele modele = new Modele();

        BorderPane borderPane = new BorderPane();

        // menu en haut
        TopBarre vueTopBarre = new TopBarre(modele);
        borderPane.setTop(vueTopBarre);
        modele.enregistrerObservateur(vueTopBarre);

        // explorateur de fichier a gauche
        VueRepCourant vueRepCourant = new VueRepCourant(modele);
        borderPane.setLeft(vueRepCourant);
        BorderPane.setMargin(vueRepCourant,new Insets(0,0,5,3));
        vueRepCourant.actualiser();
        modele.enregistrerObservateur(vueRepCourant);

        // panneau central de l'application
        Pane paneCenter = new Pane();
        SIZEX=paneCenter.getLayoutX();
        SIZEY=paneCenter.getLayoutY();
        Classe classe = new Classe("mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.Themes.ThemeClair");
        Classe classe2 = new Classe("mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.Themes.ThemeSombre");
        VBox vBoxclasse = new VueClasse(classe);
        VBox vBoxclasse2 = new VueClasse(classe2);
        paneCenter.getChildren().add(vBoxclasse);
        borderPane.setCenter(paneCenter);


        Scene scene = new Scene(borderPane,1000,800);
        stage.setScene(scene);
        stage.setTitle("DiagMov'");
        stage.setMinWidth(310);
        stage.setMinHeight(400);
        stage.setMaximized(true);
        stage.show();

        vueRepCourant.majBoutonParent();
    }

    public static void main(String[] args) {
        launch(args);
    }
}