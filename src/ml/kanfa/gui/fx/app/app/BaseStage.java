package ml.kanfa.gui.fx.app.app;

import ml.kanfa.utils.verifier.IdGen;
import ml.kanfa.utils.verifier.VerifierInterface;
import ml.kanfa.utils.verifier.WindowOpenedVerifier;

/**
 * @author Ibrahim Maïga.
 */
public abstract class BaseStage extends ApplicationComponent implements VerifierInterface{

    public BaseStage(){
        super();
        addOpened();
        this.setOnCloseRequest(event -> close());
    }

    @Override public int id() {
        return IdGen.getId(this);
    }

    @Override public void addOpened() {
        WindowOpenedVerifier.addOpened(this);
    }

    @Override public void close() {
        WindowOpenedVerifier.dispose(this);
    }
}
