package views.app;

import utils.verifier.IdGen;
import utils.verifier.VerifierInterface;
import utils.verifier.WindowOpenedVerifier;

/**
 * @author Ibrahim Maïga.
 */

public abstract class BaseStage extends ApplicationComponent implements VerifierInterface{

    public BaseStage(){
        super();
        WindowOpenedVerifier.addOpened(this);
        this.setOnCloseRequest(event -> WindowOpenedVerifier.dispose(this));
    }

    @Override public int id() {
        return IdGen.getId(this);
    }
}
