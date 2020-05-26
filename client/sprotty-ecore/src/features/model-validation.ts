/********************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
import { GLSPActionDispatcher, TYPES } from "@eclipse-glsp/client/lib";
import { inject, injectable } from "inversify";
import { EditLabelUI} from "sprotty/lib";

@injectable()
export class EditLabelUIModelValidation extends EditLabelUI {

    protected showAutocomplete: boolean = false;
    protected outerDiv: HTMLElement;
    protected listContainer: HTMLElement;
    protected currentFocus: number;
    protected types: string[] = [];


    constructor(@inject(TYPES.IActionDispatcher) protected actionDispatcher: GLSPActionDispatcher) {
        super();
    }

    protected validateLabelIfContentChange(event: KeyboardEvent, value: string) {
        if (value.length >= 10) {
            window.alert("Too long");
        }
        super.validateLabelIfContentChange(event, value);
    }
}