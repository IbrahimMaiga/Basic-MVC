package ml.kanfa.views.app;

import ml.kanfa.utils.verifier.IdGen;
import ml.kanfa.utils.verifier.VerifierInterface;
import ml.kanfa.utils.verifier.WindowOpenedVerifier;

/**
 * @author Kanfa.
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
