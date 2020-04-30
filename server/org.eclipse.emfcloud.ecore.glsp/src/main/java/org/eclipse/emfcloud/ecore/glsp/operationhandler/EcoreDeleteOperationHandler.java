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
package org.eclipse.emfcloud.ecore.glsp.operationhandler;

<<<<<<< HEAD
import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
=======
import org.eclipse.emf.ecore.EClass;
>>>>>>> 20138ebc87c44d49a8e8040d657f371165c80eac
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.emfcloud.ecore.enotation.NotationElement;
import org.eclipse.emfcloud.ecore.glsp.model.EcoreModelState;
import org.eclipse.emfcloud.ecore.glsp.util.EcoreEdgeUtil;
import org.eclipse.emfcloud.ecore.glsp.util.EcoreConfig.Types;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.server.operationhandler.DeleteOperationHandler;

public class EcoreDeleteOperationHandler extends DeleteOperationHandler {
	@Override
	protected boolean delete(String elementId, GModelIndex index, GraphicalModelState graphicalModelState) {
<<<<<<< HEAD
		super.delete(elementId, index, graphicalModelState);
		EcoreModelState modelState = EcoreModelState.getModelState(graphicalModelState);
		
		Optional<EObject> semantic = modelState.getIndex().getSemantic(elementId);
		Optional<NotationElement> notation = modelState.getIndex().getNotation(elementId);
		
		semantic.ifPresentOrElse(element -> {
			if(element instanceof EReference && ((EReference) element).getEOpposite() != null) {
				EcoreUtil.delete(((EReference) element).getEOpposite());
				modelState.getIndex().getBidirectionalReferences()
						.remove(EcoreEdgeUtil.getStringId((EReference) element));
				if (element instanceof EClassifier) {
					// Manually clean-up all EReferences/EAttributes typed by this classifier, to avoid leaving untyped
					// features (Which is legal although invalid in EMF; and not well supported by Ecore GLSP Diagrams)
					Collection<Setting> usages = UsageCrossReferencer.find(element, element.eResource().getResourceSet());
					for (Setting setting : usages) {
						if (setting.getEStructuralFeature().isChangeable() && setting.getEObject() instanceof EStructuralFeature) {
							EObject settingSource = setting.getEObject();
							EcoreUtil.delete(settingSource);
						}
					}
				}
=======

		super.delete(elementId, index, graphicalModelState);
		EcoreModelState modelState = EcoreModelState.getModelState(graphicalModelState);

		modelState.getIndex().getSemantic(elementId).ifPresentOrElse(element -> {
			if (element instanceof EReference && ((EReference) element).getEOpposite() != null) {
				EcoreUtil.delete(((EReference) element).getEOpposite());
				modelState.getIndex().getBidirectionalReferences()
						.remove(EcoreEdgeUtil.getStringId((EReference) element));
>>>>>>> 20138ebc87c44d49a8e8040d657f371165c80eac
			}
		}, () -> {
			//just for Debugging
			GModelIndex modelIndex = modelState.getIndex();

			modelState.getIndex().findElementByClass(elementId, GEdge.class).ifPresent(edge -> {
				if (edge.getType().equals(Types.INHERITANCE)) {
					modelState.getIndex().getSemantic(edge.getSourceId(), EClass.class).ifPresent(source -> {
						EClass target = modelState.getIndex().getSemantic(edge.getTargetId(), EClass.class).get();
						source.getESuperTypes().remove(target);
					});
				}
			});
		});

<<<<<<< HEAD
		notation.ifPresent(EcoreUtil::remove);
		semantic.ifPresent(EcoreUtil::remove);
=======
		modelState.getIndex().getSemantic(elementId).ifPresent(EcoreUtil::remove);
		modelState.getIndex().getNotation(elementId).ifPresent(EcoreUtil::remove);
>>>>>>> 20138ebc87c44d49a8e8040d657f371165c80eac
		return true;
	}

}
