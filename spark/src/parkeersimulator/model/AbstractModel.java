package parkeersimulator.model;

import java.util.ArrayList;
import java.util.List;

import parkeersimulator.view.*;

public class AbstractModel {

    private List<AbstractView> views = new ArrayList();

    public AbstractModel(){}

    public void addView(AbstractView view) { this.views.add(view); }

    public void notifyViews() {
        for (AbstractView view: views) {
            view.updateView();
        }
    }
}
