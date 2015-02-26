/* Copyright 2012 VMware, Inc. All rights reserved. -- VMware Confidential */

package com.acme.uI.views {

import com.vmware.core.model.IResourceReference;
import com.vmware.ui.IContextObjectHolder;

import flash.events.Event;
import flash.events.EventDispatcher;
import flash.events.MouseEvent;

import mx.controls.Alert;
import mx.logging.ILogger;
import mx.logging.Log;

/**
 * The mediator for UIView
 */
public class UIMediator extends EventDispatcher implements IContextObjectHolder
{
   private var _contextObject:IResourceReference;
   private var _view:UIView;

   private static var _logger:ILogger = Log.getLogger('UIMediator');

   [View]
   /** The view associated with this mediator */
   public function get view():UIView {
      return _view;
   }

   /** @private */
   public function set view(value:UIView):void {
      // The view is injected here by the Framework when it is first created,
      // and reset to null when it is no longer needed.
      if ((value == null) && (_view != null)) {
         _view.showSelected.removeEventListener(MouseEvent.CLICK, onButtonClick);
      }

      _view = value;

      if (_view != null) {
         _view.showSelected.addEventListener(MouseEvent.CLICK, onButtonClick);
      }
   }


   [Bindable]
   /** Returns the inventory object handled in this view (IContextObjectHolder interface) */
   public function get contextObject():Object {
      return _contextObject;
   }

   /** Called by the framework with the current inventory object or null */
   public function set contextObject(value:Object):void {
      _contextObject = IResourceReference(value);

      if (_contextObject == null) {
         // A null contextObject means that the view is being cleared
         clearData();
         return;
      }

      // Once contextObject is set the view can be initialized with the object data.
      requestData();
   }

   private function requestData():void {
      // Add code to initialize your UI data, for instance dispatch a data request event
      // to fetch data from the server.
   }

   private function clearData() : void {
      // Add code to clear your UI data
   }

   private function onButtonClick(click:MouseEvent):void {
      // Simple alert to display the selected object.
      // Getting more data requires a server call.
      Alert.show("Context object is " + _contextObject.uid);
   }

}
}