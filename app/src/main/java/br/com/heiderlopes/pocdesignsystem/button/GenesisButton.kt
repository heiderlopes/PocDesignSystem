package br.com.heiderlopes.pocdesignsystem.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import br.com.heiderlopes.pocdesignsystem.R
import com.airbnb.lottie.LottieAnimationView


class GenesisButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    enum class GenesisButtonType(val type: Int) {
        PRIMARY(0),
        SECONDARY(1);

        companion object {
            private val map = values().associateBy(GenesisButtonType::type)
            fun from(type: Int) = map[type]
        }
    }

    private val progressBar: LottieAnimationView
    private val buttonTextView: TextView
    private val container: RelativeLayout

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.genesis_button, this, true)

        buttonTextView = root.findViewById(R.id.button_text)
        progressBar = root.findViewById(R.id.progress_indicator)
        container = root.findViewById(R.id.container)

        loadAttr(attrs, defStyleAttr)
    }

    private fun loadAttr(attrs: AttributeSet?, defStyleAttr: Int) {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.GenesisButton,
            defStyleAttr,
            0
        )

        val buttonText = arr.getString(R.styleable.GenesisButton_text)
        val type = arr.getInt(R.styleable.GenesisButton_type, 0)
        val loading = arr.getBoolean(R.styleable.GenesisButton_loading, false)
        val lottieResId =
            arr.getResourceId(R.styleable.GenesisButton_lottie_resId, R.raw.lottile_button_loader)

        arr.recycle()
        setButtonType(type)
        setText(buttonText)
        progressBar.setAnimation(lottieResId)
        setLoading(loading)
    }

    fun setButtonType(type: Int) {
        val genesisButtonType = GenesisButtonType.from(type)
        when (genesisButtonType) {
            GenesisButtonType.PRIMARY -> {
                container.background = ContextCompat.getDrawable(context, R.drawable.primary_button_selector)
            }
            GenesisButtonType.SECONDARY -> {
                container.background = ContextCompat.getDrawable(context, R.drawable.secondary_button_selector)
            }
        }
    }

    fun setLoading(loading: Boolean) {
        isClickable = !loading //Disable clickable when loading
        if (loading) {
            buttonTextView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            buttonTextView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    fun setText(text: String?) {
        buttonTextView.text = text
    }
}