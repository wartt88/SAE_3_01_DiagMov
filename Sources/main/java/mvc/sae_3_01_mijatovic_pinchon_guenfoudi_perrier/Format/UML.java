package mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.Format;

import mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.interfacesETabstract.Format;
import mvc.sae_3_01_mijatovic_pinchon_guenfoudi_perrier.model.Modele;

public class UML implements Format {

    @Override
    public void exporter(Modele m, String chemin) {

    }

    @Override
    public String getNom() {
        return "UML";
    }

}
