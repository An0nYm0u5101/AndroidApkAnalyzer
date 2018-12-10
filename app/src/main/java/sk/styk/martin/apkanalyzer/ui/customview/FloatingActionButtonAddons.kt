package sk.styk.martin.apkanalyzer.ui.customview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.fab_container.view.*
import kotlinx.android.synthetic.main.fab_menu_item_icon.view.*
import kotlinx.android.synthetic.main.floating_action_button.view.*
import kotlinx.android.synthetic.main.list_item_fab_menu.view.*
import sk.styk.martin.apkanalyzer.R
import sk.styk.martin.apkanalyzer.util.DP

/**
 * @author Martin Styk {@literal <martin.styk@gmail.com>}
 */
interface SpeedDialMenuOpenListener {
    fun onOpen(floatingActionButton: FloatingActionButton)
}

interface SpeedDialMenuCloseListener {
    fun onClose(floatingActionButton: FloatingActionButton)
}

abstract class SpeedDialMenuAdapter {

    /**
     * Gets the number of items represented by this adapter.
     * Note: returning zero has the same effect as return `false` from `isEnabled()`.
     * @return the number of items represented by this adapter
     */
    abstract fun getCount(): Int

    /**
     * Gets the menu item to display at the specified position in the range 0 to `getCount() - 1`.
     * See `SpeedDialMenuItem` for more details.
     * Note: positions start at zero closest to the FAB and increase for items further away.
     * @return the menu item to display at the specified position
     */
    abstract fun getMenuItem(context: Context, position: Int): SpeedDialMenuItem

    /**
     * Handler for click events on menu items.
     * The position passed corresponds to positions passed to `getMenuItem()`.
     * @return `true` to close the menu after the click; `false` to leave it open
     */
    open fun onMenuItemClick(position: Int): Boolean = true

    /**
     * Gets the colour of the background tile behind the menu item.
     * Positions correspond to positions passed to `getMenuItem()`.
     * Note: this method should return an aRGB colour integer, *not* a colour resource ID.
     * @return the colour of the card behind the icon at the specified position
     */
    @ColorInt
    open fun getBackgroundColour(position: Int, context: Context): Int = Color.argb(255, 192, 192, 192)

    /**
     * Apply formatting to the `TextView` used for the label of the menu item at the given position.
     * Note: positions start at zero closest to the FAB and increase for items further away.
     */
    open fun onPrepareItemLabel(context: Context, position: Int, label: TextView) {}

    /**
     * Apply formatting to the view used for the card behind the icon at the given position.
     * Note: the view will be a `CardView` on SDK 21+ and a `LinearLayout` on SDK 20 and below.
     * Note: positions start at zero closest to the FAB and increase for items further away.
     */
    open fun onPrepareItemCard(context: Context, position: Int, card: View) {}

    /**
     * Apply formatting to the `LinearLayout` that wraps the icon of the menu item at the given position.
     * This is called after the icon is set as the background of the given wrapper.
     * Note: positions start at zero closest to the FAB and increase for items further away.
     */
    open fun onPrepareItemIconWrapper(context: Context, position: Int, label: LinearLayout) {}

    /**
     * Gets the number of degrees to rotate the FAB's icon when the speed-dial menu opens.
     * This is useful for the popular "plus icon"/"close icon" transition.
     * @return the number of degrees to rotate the FAB's icon when the speed-dial menu opens
     */
    open fun fabRotationDegrees() = 0F

    /**
     * Determines whether or not the speed-dial menu is enabled.
     * @return `true` if the menu is enabled; `false` if it is not
     */
    open fun isEnabled() = true

}

class SpeedDialMenuItem(private val context: Context) {

    private var icon: Drawable? = null
    private var label: String? = null

    constructor(context: Context, icon: Drawable, label: String) : this(context) {
        setIcon(icon)
        setLabel(label)
    }

    constructor(context: Context, icon: Drawable, @StringRes label: Int) : this(context) {
        setIcon(icon)
        setLabel(label)
    }

    constructor(context: Context, @DrawableRes icon: Int, label: String) : this(context) {
        setIcon(icon)
        setLabel(label)
    }

    constructor(context: Context, @DrawableRes icon: Int, @StringRes label: Int) : this(context) {
        setIcon(icon)
        setLabel(label)
    }

    fun setIcon(icon: Drawable) {
        this.icon = icon
    }

    fun setIcon(@DrawableRes icon: Int) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.icon = context.resources.getDrawable(icon, context.theme)
        } else {
            @Suppress("DEPRECATION")
            this.icon = context.resources.getDrawable(icon)
        }
    }

    fun getIcon() = icon

    fun setLabel(label: String) {
        this.label = label
    }

    fun setLabel(@StringRes label: Int) {
        this.label = context.getString(label)
    }

    fun getLabel() = label

}

