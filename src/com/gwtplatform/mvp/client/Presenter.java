/**
 * Copyright 2010 ArcBees Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.mvp.client;

import com.google.inject.Singleton;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;

/**
 * A singleton presenter, one of the basic building block of the <a href=
 * "http://code.google.com/intl/nl/events/io/2009/sessions/GoogleWebToolkitBestPractices.html"
 * > model-view-presenter</a> architecture. Each page in your application should
 * correspond to a singleton {@link Presenter} and it should have an
 * accompanying singleton {@link View} and {@link Proxy}.
 * 
 * @param <V> The {@link View} type.
 * 
 * @author Philippe Beaudoin
 */
@Singleton
public interface Presenter<V extends View> extends PresenterWidget<V> {

  /**
   * This method can be used to reveal any presenter. This call will go up the
   * hierarchy, revealing any parent of this presenter. This method will also
   * bypass the change confirmation mechanism (see {@link PlaceManager#setOnLeaveConfirmation(String)}).
   * 
   * <b>Important:</b> If you're using this method to reveal a place, this
   * method will not update the browser history. Consider using
   * {@link PlaceManager#revealPlace(PlaceRequest)}.
   */
  void forceReveal();

  /**
   * Returns the {@link Proxy} for the current presenter.
   * 
   * @return The proxy.
   */
  Proxy<?> getProxy();

  /**
   * This method is called when a {@link Presenter} should prepare itself based
   * on a {@link PlaceRequest}. The presenter should extract any parameters it
   * needs from the request. A presenter should override this method if it
   * handles custom parameters, but it should call the parent's
   * {@code prepareFromRequest} method.
   * 
   * @param request The request.
   */
  void prepareFromRequest(PlaceRequest request);

  /**
   * This method is called when creating a {@link PlaceRequest} for this
   * {@link Presenter}. The presenter should add all the required parameters to
   * the request.
   * <p/>
   * <p/>
   * If nothing is to be done, simply return the {@code request} unchanged.
   * Otherwise, call {@link PlaceRequest#with(String, String)} to add
   * parameters. Eg:
   * <p/>
   * 
   * <pre>
   * return request.with( &quot;id&quot;, getId() );
   * </pre>
   * <p/>
   * A presenter should override this method if it handles custom parameters,
   * but it should call the parent's {@code prepareRequest} method.
   * 
   * @param request The current request.
   * @return The prepared place request.
   */
  PlaceRequest prepareRequest(PlaceRequest request);
}
