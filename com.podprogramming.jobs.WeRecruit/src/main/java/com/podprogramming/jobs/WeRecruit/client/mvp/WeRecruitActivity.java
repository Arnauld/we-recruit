package com.podprogramming.jobs.WeRecruit.client.mvp;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.RadioButton;

public class WeRecruitActivity extends
		HandlerRegistrationActivity<IWeRecruitActivity.IWeRecruitView>
		implements IWeRecruitActivity<IWeRecruitActivity.IWeRecruitView> {
	
	private final byte[] message = {10, 9, 9, 60, 98, 114, 47, 62, 78, 111, 
	        117, 115, 32, 118, 111, 117, 108, 111, 110, 115, 
	        32, 116, 114, 97, 118, 97, 105, 108, 108, 101, 
	        114, 32, 97, 118, 101, 99, 32, 118, 111, 117, 
	        115, 33, 32, 69, 110, 118, 111, 121, 101, 122, 
	        32, 110, 111, 117, 115, 32, 118, 111, 116, 114, 
	        101, 32, 67, 86, 44, 32, 118, 111, 116, 114, 
	        101, 32, 112, 97, 103, 101, 32, 103, 105, 116, 
	        104, 117, 98, 44, 32, 108, 39, 97, 100, 114, 
	        101, 115, 115, 101, 32, 100, 101, 32, 118, 111, 
	        116, 114, 101, 32, 98, 108, 111, 103, 44, 32, 
	        118, 111, 116, 114, 101, 32, 99, 111, 109, 112, 
	        116, 101, 32, 116, 119, 105, 116, 116, 101, 114, 
	        44, 32, 101, 116, 32, 116, 111, 117, 116, 32, 
	        99, 101, 32, 113, 117, 105, 32, 118, 111, 117, 
	        115, 32, 112, 97, 114, 97, -61, -82, 116, 32, 
	        105, 110, 116, -61, -87, 114, 101, 115, 115, 97, 
	        110, 116, 32, -61, -96, 32, 58, 60, 98, 114, 
	        47, 62, 60, 97, 32, 104, 114, 101, 102, 61, 
	        34, 109, 97, 105, 108, 116, 111, 58, 114, 101, 
	        99, 114, 117, 105, 116, 109, 101, 110, 116, 64, 
	        112, 111, 100, 45, 112, 114, 111, 103, 114, 97, 
	        109, 109, 105, 110, 103, 46, 99, 111, 109, 34, 
	        62, 114, 101, 99, 114, 117, 105, 116, 109, 101, 
	        110, 116, 64, 112, 111, 100, 45, 112, 114, 111, 
	        103, 114, 97, 109, 109, 105, 110, 103, 46, 99, 
	        111, 109, 60, 47, 97, 62, 10, 9};
	
	/**
	 * Constructs an instance of the activity corresponding to the given place
	 * 
	 * @param place
	 */
	public WeRecruitActivity( WeRecruitPlace place ) {
		super( Factory.getEventBus(), Factory.weRecruitView() );
	}

	/**
	 * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget,
	 *      com.google.gwt.event.shared.EventBus)
	 */
	@Override
	public void start( AcceptsOneWidget containerWidget, EventBus eventBus ) {
		// resets the view
		view.reset();

		// event handler registration
		// 1. creates a boolean change handler
		ValueChangeHandler<Boolean> booleanChangeHandler = new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event ) {
				// updates the combination label
				updateCombinationLabel();
			}
		};
		// 2. registers the boolean change handler on the selectable items of
		// the view
		for(RadioButton button : view.getQuestionRadios()) {
		    registerHandler( button.addValueChangeHandler(booleanChangeHandler));
		}

		// attaches the view in the container widget of the application
		containerWidget.setWidget( view.asWidget() );
	}

	/**
	 * @see com.podprogramming.jobs.WeRecruit.client.mvp.IWeRecruitActivity#updateCombinationLabel()
	 */
	@Override
	public void updateCombinationLabel() {
		// retrieves the state of the selectable items of the view
		boolean youWin = view.q1Answer1HasValue().getValue()
							&& view.q2Answer1HasValue().getValue()
							&& view.q3Answer3HasValue().getValue()
							&& view.q4Answer2HasValue().getValue()
							&& view.q5Answer2HasValue().getValue();
		// computes the combination message
		String combinationMessage = "";
		if( youWin ) {
			try {
                combinationMessage = new String(message);
            } catch (Exception e) {
                combinationMessage = "You win but the secret part cannot be revealed..." + e;
            }
		}

		// sets the resulting message in the view
		view.mailToHasHTML().setHTML( combinationMessage );
		view.asWidget().setVisible(true);
	}
}