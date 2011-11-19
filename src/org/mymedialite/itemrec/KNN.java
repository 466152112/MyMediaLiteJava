// Copyright (C) 2010, 2011 Zeno Gantner
// Copyright (C) 2011 Chris Newell
//
// This file is part of MyMediaLite.
//
// MyMediaLite is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// MyMediaLite is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with MyMediaLite.  If not, see <http://www.gnu.org/licenses/>.

package org.mymedialite.itemrec;

import java.io.*;

import org.mymedialite.correlation.CorrelationMatrix;
import org.mymedialite.itemrec.ItemRecommender;
import org.mymedialite.util.Recommender;

/**
 * Base class for item recommenders that use some kind of kNN model.
 */
public abstract class KNN extends ItemRecommender {

	/**
	 * The number of neighbors to take into account for prediction.
	 */
	protected int k = 80;

	/**
	 * Pre-computed nearest neighbors.
	 */
	protected int[][] nearest_neighbors;

	/**
	 * Correlation matrix over some kind of entity.
	 */
	protected CorrelationMatrix correlation;

	/** { @inheritDoc } */
	public void saveModel(String filename) throws IOException {
		PrintWriter writer = Recommender.getWriter(filename, this.getClass());
		saveModel(writer);
	}

	/** { @inheritDoc } */
	public void saveModel(PrintWriter writer) {
		writer.println(nearest_neighbors.length);
		for (int[] nn : nearest_neighbors) {
			writer.write(nn[0]);
			for (int i = 1; i < nn.length; i++)
				writer.print(" " + nn[i]);
			writer.println();
		}
		correlation.write(writer);
	}

	/** { @inheritDoc } */
	public void loadModel(String filename) throws IOException {
		BufferedReader reader = Recommender.getReader(filename, this.getClass());
		loadModel(reader);
	}

	/** { @inheritDoc } */
	public void loadModel(BufferedReader reader) throws IOException {
		int num_users = Integer.parseInt(reader.readLine());
		int[][] nearest_neighbors = new int[num_users][];
		for (int u = 0; u < nearest_neighbors.length; u++) {
			String[] numbers = reader.readLine().split(" ");
			nearest_neighbors[u] = new int[numbers.length];
			for (int i = 0; i < numbers.length; i++) {
				nearest_neighbors[u][i] = Integer.parseInt(numbers[i]);
			}
		}
		this.correlation = CorrelationMatrix.readCorrelationMatrix(reader);
		this.k = nearest_neighbors[0].length;
		this.nearest_neighbors = nearest_neighbors;
	}
}
