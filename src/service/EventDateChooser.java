package service;

import view.component.calendar.SelectedDate;

public interface EventDateChooser {

    public void dateSelected(SelectedAction action, SelectedDate date);
}
