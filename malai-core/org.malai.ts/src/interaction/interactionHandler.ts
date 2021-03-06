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
namespace malai {
    /**
     * Defines an interaction for objects that want to by notified when the state of an interaction changed.
     * @author Arnaud BLOUIN
     */
    export interface InteractionHandler {
        /**
         * When the interaction quits its initial state.
         * @param interaction The concerned interaction.
         * @throws MustAbortStateMachineException If the interaction must be aborted.
         */
        interactionStarts(interaction : Interaction) : void;

        /**
         * When the interaction goes to standard state.
         * @param interaction The concerned interaction.
         * @throws MustAbortStateMachineException If the interaction must be aborted.
         */
        interactionUpdates(interaction : Interaction) : void;

        /**
         * When the interaction goes to a terminal state.
         * @param interaction The concerned interaction.
         * @throws MustAbortStateMachineException If the interaction must be aborted.
         */
        interactionStops(interaction : Interaction) : void;

        /**
         * When the interaction goes to an aborting state.
         * @param interaction The concerned interaction.
         */
        interactionAborts(interaction : Interaction) : void;
    }
}

