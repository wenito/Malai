/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.binding;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import javafx.scene.control.MenuItem;
import org.malai.action.ActionImpl;
import org.malai.interaction.Interaction;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.MenuItemInteraction;

/**
 * This anonymous widget binding for menus takes a function as a parameter that will be executed to initialise the action.
 * The goal is to avoid the creation of a specific class when the widget binding is quite simple.
 * @author Arnaud Blouin
 */
public class JFxAnonMenuBinding<A extends ActionImpl, I extends MenuItemInteraction<MenuItem>, N extends JfxInstrument> extends JfxMenuItemBinding<A, I, N> {
	private final BiConsumer<A, I> execInitAction;
	private final Predicate<I> checkInteraction;
	private final BiConsumer<A, I> onEnd;

	/**
	 * Creates a menu item binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param clazzInteraction The type of the interaction that will be created. Used to instantiate the interaction by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param menus The menus used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonMenuBinding(final N ins, final boolean exec, final Class<A> clazzAction, final Class<I> clazzInteraction,
							  final BiConsumer<A, I> initActionFct, final Predicate<I> check, final BiConsumer<A, I> onEndFct,
							  final List<MenuItem> menus) throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, clazzInteraction, menus);
		execInitAction = initActionFct == null ? (a, i) -> {} : initActionFct;
		checkInteraction = check == null ? i -> true : check;
		onEnd = onEndFct;
	}

	@Override
	public void initAction() {
		execInitAction.accept(getAction(), getInteraction());
	}

	@Override
	public boolean isConditionRespected() {
		return checkInteraction == null || checkInteraction.test(getInteraction());
	}


	@Override
	public void interactionStops(final Interaction inter) {
		final A action = getAction();
		super.interactionStops(inter);
		if(onEnd != null) {
			onEnd.accept(action, getInteraction());
		}
	}

	@Override
	public String toString() {
		return "JFxAnonMenuBinding in " + instrument + '{' + interaction + " -> " + clazzAction + '}';
	}
}
