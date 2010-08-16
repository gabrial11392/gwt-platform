/**
 * Copyright 2010 Gwt-Platform
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gwtplatform.mvp.client;

import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public abstract class PresenterImpl<V extends View, Proxy_ extends Proxy<?>> 
extends PresenterWidgetImpl<V> implements Presenter {

  /**
   * The light-weight {@PresenterProxy} around this presenter.
   */
  protected final Proxy_ proxy;
  
  /**
   * Creates a basic {@link Presenter}.
   * @param eventBus The event bus.
   * @param view  The view attached to this presenter.
   * @param proxy The presenter proxy.
   */
  public PresenterImpl( 
      final EventBus eventBus, 
      final V view, 
      final Proxy_ proxy ) {
    super(eventBus, view);
    this.proxy = proxy;
  }
  
  @Override
  public final Proxy_ getProxy() {
    return proxy;
  }

  /**
   * <b>Important:</b> This method is only meant to be used internally by GWTP.
   * If you wish to reveal a presenter, call {@link PlaceManager#revealPlace(PlaceRequest)} 
   * or a related method. This will ensure you don't inadvertently reveal a 
   * non-leaf presenter. Also, you will benefit from the change confirmation
   * mechanism. (See {@link PlaceManager#setOnLeaveConfirmation(String)}).
   * <p />
   * Forces the presenter to reveal itself on screen.
   */  
  public final void forceReveal() {
    if( isVisible() )
      return;
    revealInParent();
  }
  
  @Override
  protected void onReset() {
    super.onReset();
    getProxy().onPresenterRevealed( this );
  }
  
  /**
   * Called whenever the presenter needs to set its content in 
   * a parent. You need to override this method. You should 
   * usually fire a {@link RevealContentEvent}.
   */
  protected abstract void revealInParent();

  /**
   * <b>Deprecated!</b> This method will soon be removed from the API. For more
   * information see
   * <a href="http://code.google.com/p/gwt-platform/issues/detail?id=136">Issue 136</a>.
   * <p />
   * Notify others that this presenter has been changed. This is especially
   * useful for stateful presenters that store parameters within the
   * history token. Calling this will make sure the history token is
   * updated with the right parameters.
   */
  @Deprecated
  protected final void notifyChange() {
    getProxy().onPresenterChanged( this );
  }
  

  /**
   * This method is called when a {@link Presenter} should prepare itself
   * based on a {@link PlaceRequest}. The presenter should extract
   * any parameters it needs from the request. A presenter should override
   * this method if it handles custom parameters, but it should call
   * the parent's {@code prepareFromRequest} method.
   *
   * @param request   The request.
   */
  public void prepareFromRequest( PlaceRequest request ) {
    // By default, no parameter to extract from request.
  }

  
  /**
   * This method is called when creating a {@link PlaceRequest} for this
   * {@link Presenter}. The presenter should add all the required parameters to the 
   * request.
   * <p/>
   * <p/>
   * If nothing is to be done, simply return the {@code request}
   * unchanged. Otherwise, call {@link PlaceRequest#with(String, String)} to
   * add parameters. Eg:
   * <p/>
   * <pre>
   * return request.with( &quot;id&quot;, getId() );
   * </pre>
   * <p/>
   * A presenter should override this method if it handles custom parameters, but
   * it should call the parent's {@code prepareRequest} method.
   * 
   * @param request   The current request.
   * @return The prepared place request.
   */
  public PlaceRequest prepareRequest( PlaceRequest request ) {
    // By default, no parameter to add to request
    return request;
  }

  
}
