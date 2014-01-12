/*
 * @(#)TopGolfModule.java}
 *
 * Copyright (c) 2011 Experion Technologies Pvt. Ltd.
 * 407,Thejaswini, Technopark Campus, Trivandrum 695 581
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Experion Technologies Pvt. Ltd. You shall not disclose such
 * Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into
 * with Experion Technologies.
 */


import navigationcontroller.NavigationConfigBase;
import navigationcontroller.NavigationConfiguration;
import navigationcontroller.NavigationController;
import navigationcontroller.NavigationControllerImpl;
import repository.DatabaseHelper;
import repository.Repository;
import view.helper.ViewHelper;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.util.Types;

/**
 * 
 * @author arunkumar.s <dt><b>Date</b>
 *         <dd>Apr 1, 2013, 3:10:07 PM
 *         <dt><b>Module</b>
 *         <dd>TopGolfMobile
 *         <dt>TODO
 *         <dd>
 */
public class TopGolfModule extends AbstractModule {

	@Override
	protected void configure() {

		/* Add binding of activities here. */
		bind(ViewHelper.class).asEagerSingleton();

		/* Add binding of Navigations here. */
		bind(NavigationConfigBase.class).to(NavigationConfiguration.class).asEagerSingleton();
		bind(NavigationController.class).to(NavigationControllerImpl.class).asEagerSingleton();

		/* Add binding of service to service classes here. */
//		bind(UserService.class).to(UserServiceImpl.class).asEagerSingleton();

		/* Add binding of database related things here. */
		bind(SQLiteOpenHelper.class).to(DatabaseHelper.class);

		/* Add binding of data class to repository here. */
//		bind(repositoryOf(Login.class)).to(LoginRepository.class);
	}

	/**
	 * Create a key for Repository.
	 * @param <T> the generic type
	 * @param type the type
	 * @return the key
	 */
	@SuppressWarnings("unchecked")
	static <T> Key<Repository<T>> repositoryOf(Class<T> type) {
		return (Key<Repository<T>>) Key.get(Types.newParameterizedType(Repository.class, type));
	}
}
