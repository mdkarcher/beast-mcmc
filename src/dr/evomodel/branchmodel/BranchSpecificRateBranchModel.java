/*
 * BranchSpecificRateBranchModel.java
 *
 * Copyright (c) 2002-2018 Alexei Drummond, Andrew Rambaut and Marc Suchard
 *
 * This file is part of BEAST.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * BEAST is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 *  BEAST is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with BEAST; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package dr.evomodel.branchmodel;

import dr.evolution.tree.NodeRef;
import dr.evomodel.branchratemodel.BranchSpecificRateSubstitutionModel;
import dr.evomodel.substmodel.FrequencyModel;
import dr.evomodel.substmodel.SubstitutionModel;
import dr.inference.model.AbstractModel;
import dr.inference.model.Model;
import dr.inference.model.Parameter;
import dr.inference.model.Variable;

import java.util.List;

/**
 * @author Marc Suchard
 * @author Xiang Ji
 */
public class BranchSpecificRateBranchModel extends AbstractModel implements BranchModel {

    private final BranchSpecificRateSubstitutionModel substitutionModel;

    public BranchSpecificRateBranchModel(String name, BranchSpecificRateSubstitutionModel substitutionModel) {
        super(name);
        this.substitutionModel = substitutionModel;
    }


    @Override
    public Mapping getBranchModelMapping(NodeRef branch) {
        return substitutionModel.getBranchModelMapping(branch);
    }

    @Override
    public List<SubstitutionModel> getSubstitutionModels() {
        return substitutionModel.getSubstitutionModelList();
    }

    @Override
    public SubstitutionModel getRootSubstitutionModel() {
        return substitutionModel.getRootSubstitutionModel();
    }

    @Override
    public FrequencyModel getRootFrequencyModel() {
        return getRootSubstitutionModel().getFrequencyModel();
    }

    @Override
    public boolean requiresMatrixConvolution() {
        return false;
    }

    @Override
    protected void handleModelChangedEvent(Model model, Object object, int index) {

    }

    @Override
    protected void storeState() {

    }

    @Override
    protected void restoreState() {

    }

    @Override
    protected void acceptState() {

    }

    @Override
    protected void handleVariableChangedEvent(Variable variable, int index, Parameter.ChangeType type) {

    }
}
