/*
 * DiagonalPrecisionGradient.java
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

package dr.evomodel.treedatalikelihood.hmc;

import dr.inference.model.CompoundSymmetricMatrix;
import dr.inference.model.Likelihood;
import dr.math.MultivariateFunction;
import dr.math.NumericalDerivative;
import dr.math.interfaces.ConjugateWishartStatisticsProvider;
import dr.math.matrixAlgebra.SymmetricMatrix;
import dr.math.matrixAlgebra.Vector;

/**
 * @author Paul Bastide
 * @author Marc A. Suchard
 */
public class DiagonalPrecisionGradient extends AbstractPrecisionGradient {

    public DiagonalPrecisionGradient(ConjugateWishartStatisticsProvider wishartStatistics,
                                     Likelihood likelihood,
                                     CompoundSymmetricMatrix parameter) {

        super(wishartStatistics, likelihood, parameter);
    }

    @Override
    public int getDimension() {
        return getDimensionDiagonal();
    }

    @Override
    double[] getGradientParameter(SymmetricMatrix weightedSumOfSquares,
                                  int numberTips,
                                  SymmetricMatrix correlationPrecision,
                                  double[] precisionDiagonal) {

        return getGradientDiagonal(weightedSumOfSquares, numberTips,
                correlationPrecision, precisionDiagonal);
    }

    MultivariateFunction getNumeric() {

        return new MultivariateFunction() {

            @Override
            public double evaluate(double[] argument) {

                for (int i = 0; i < argument.length; ++i) {
                    parameter.getDiagonalParameter().setParameterValue(i, argument[i]);
                }

                likelihood.makeDirty();
                return likelihood.getLogLikelihood();
            }

            @Override
            public int getNumArguments() {
                return parameter.getDiagonalParameter().getDimension();
            }

            @Override
            public double getLowerBound(int n) {
                return 0.0;
            }

            @Override
            public double getUpperBound(int n) {
                return Double.POSITIVE_INFINITY;
            }
        };
    }

    @Override
    String checkNumeric(double[] analytic) {

        System.err.println("Numeric at: \n" + new Vector(parameter.getDiagonalParameter().getParameterValues()));

        double[] storedValues = parameter.getDiagonalParameter().getParameterValues();
        double[] testGradient = NumericalDerivative.gradient(getNumeric(), storedValues);
        for (int i = 0; i < storedValues.length; ++i) {
            parameter.getDiagonalParameter().setParameterValue(i, storedValues[i]);
        }

        return getReportString(analytic, testGradient);
    }
}
