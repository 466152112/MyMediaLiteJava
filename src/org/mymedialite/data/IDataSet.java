import java.util.List;

// Copyright (C) 2011 Zeno Gantner
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
//

/**
 * Interface for different kinds of collaborative filtering data sets.
 * 
 * Implementing classes/inheriting interfaces are e.g. for rating data and for positive-only implicit feedback.
 *
 * The main feature of a dataset is that it has some kind of order (not explicitly stated)
 * - random, chronological, user-wise, or item-wise - and that it contains tuples of users and
 * items (not necessarily unique tuples).
 *
 * Implementing classes and inheriting interfaces can add additional data to each user-item tuple,
 * e.g. the date/time of an event, location, context, etc., as well as additional index structures
 * to access the dataset in a certain fashion.
 * 
 * @author Zeno Gantner
 * @version 2.02
 */
public interface IDataSet
{
	/**
	 * @return the number of interaction events in the dataset.
	 */
	int getSize();
	
	/**
	 * @return the user entries.
	 */
	List<Integer> getUsers();
	/**
	 * @return the item entries.
	 */
	List<Integer> getItems();

	/**
	 * @return the maximum user ID in the dataset.
	 */
	int getMaxUserID();
	/**
	 * @return the maximum item ID in the dataset.
	 */
	int getMaxItemID();

	/**
	 * @return all user IDs in the dataset.
	 */
	List<Integer> getAllUsers();
	/**
	 * @return all item IDs in the dataset.
	 */
	List<Integer> getAllItems();

	/**
	 * indices by user.
	 * Should be implemented as a lazy data structure
	 */
	List<List<Integer>> getByUser();
	/**
	 * indices by item.
	 * Should be implemented as a lazy data structure
	 */
	List<List<Integer>> getByItem();
	/**
	 * get a randomly ordered list of all indices.
	 * Should be implemented as a lazy data structure
	 */
	List<Integer> getRandomIndex();

	/**
	 * Build the user indices.
	 */
	void buildUserIndices();
	/**
	 * Build the item indices.
	 */
	void buildItemIndices();
	/**
	 * Build the random index.
	 */
	void buildRandomIndex();

	/**
	 * Remove all events related to a given user.
	 * @param user_id the user ID
	 */
	void removeUser(int user_id);

	/**
	 * Remove all events related to a given item.
	 * @param item_id the item ID
	 */
	void removeItem(int item_id);
}
