/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ahamed.multiviewadapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.ahamed.multiviewadapter.annotation.ExpandableMode;
import com.ahamed.multiviewadapter.util.InfiniteLoadingHelper;

public class RecyclerAdapter extends CoreRecyclerAdapter {

  /**
   * Item/Group can not be expanded
   *
   * @see ExpandableMode annotation
   */
  public static final int EXPANDABLE_MODE_NONE = -1;

  /**
   * Only one item/group can be expanded at a time
   *
   * @see ExpandableMode annotation
   */
  public static final int EXPANDABLE_MODE_SINGLE = 1;

  /**
   * Multiple items/groups can be expanded at a time
   *
   * @see ExpandableMode annotation
   */
  public static final int EXPANDABLE_MODE_MULTIPLE = 2;

  /**
   * Get the {@link ItemTouchHelper} which resolves the swipe and drag gestures for the adapter's
   * individual ItemBinders.
   *
   * @return ItemTouchHelper which needs to be attached with RecyclerView
   */
  public final ItemTouchHelper getItemTouchHelper() {
    if (null == itemTouchHelper) {
      ItemBinderTouchCallback itemBinderTouchCallback = new ItemBinderTouchCallback(this);
      itemTouchHelper = new ItemTouchHelper(itemBinderTouchCallback);
    }
    return itemTouchHelper;
  }

  /**
   * Sets the number of spans to be laid out.
   * <p>
   * Based on the orientation of your {@link GridLayoutManager}'s orientation it can represent rows
   * or columns.</p>
   *
   * @param maxSpanCount The total number of spans in the grid
   */
  public final void setSpanCount(int maxSpanCount) {
    this.maxSpanCount = maxSpanCount;
  }

  /**
   * Returns the current {@link GridLayoutManager.SpanSizeLookup} used by the {@link
   * RecyclerAdapter}.<p> Default implementation sets each item to occupy exactly 1 span.</p>
   *
   * @return The {@link GridLayoutManager.SpanSizeLookup} used by the {@link RecyclerAdapter}.
   */
  public final GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
    return spanSizeLookup;
  }

  public final ItemDecorationManager getItemDecorationManager() {
    return itemDecorationManager;
  }

  /**
   * To add the {@link DataListManager} to the {@link RecyclerAdapter}
   *
   * @param dataManager The DataManager to be added to {@link RecyclerAdapter}
   */
  protected final void addDataManager(BaseDataManager dataManager) {
    dataManagers.add(dataManager);
  }

  /**
   * To register the {@link ItemBinder} to the {@link RecyclerAdapter}
   *
   * @param binder The ItemBinder to be register with {@link RecyclerAdapter}
   */
  protected final void registerBinder(ItemBinder binder) {
    addBinder(binder);
  }

  /**
   * To set the selection mode for the {@link RecyclerAdapter}
   *
   * @param expandableMode The expansion mode to be set
   * @see ExpandableMode ExpandableMode for possible values
   */
  public final void setExpandableMode(@ExpandableMode int expandableMode) {
    this.expandableMode = expandableMode;
  }

  /**
   * To set the selection mode for the {@link SelectableAdapter}
   *
   * @param groupExpandableMode The expansion mode to be set
   * @see ExpandableMode ExpandableMode for possible values
   */
  public final void setGroupExpandableMode(@ExpandableMode int groupExpandableMode) {
    this.groupExpandableMode = groupExpandableMode;
  }

  /**
   * Helper method to set the adapter is in contextual action mode.
   *
   * @see BaseViewHolder To get the state of context mode
   */
  public final void startContextMode() {
    isInContextMode = true;
  }

  /**
   * Helper method to set the adapter has exited contextual action mode.
   *
   * @see BaseViewHolder To get the state of context mode
   */
  public final void stopContextMode() {
    isInContextMode = false;
  }

  /**
   * Set
   *
   * @param infiniteLoadingHelper Helper class for bringing infinite loading feature to the adapter
   */
  public void setInfiniteLoadingHelper(InfiniteLoadingHelper infiniteLoadingHelper) {
    registerBinder(infiniteLoadingHelper.getItemBinder());
    DataItemManager<String> dataItemManager = new DataItemManager<>(this, "LoadingItem");
    addDataManager(dataItemManager);
    infiniteLoadingHelper.setDataItemManager(dataItemManager);
  }
}