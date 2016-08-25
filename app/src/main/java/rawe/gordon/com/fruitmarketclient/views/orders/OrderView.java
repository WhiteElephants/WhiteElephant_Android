package rawe.gordon.com.fruitmarketclient.views.orders;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gordon.rawe.business.models.CartOrder;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.application.ContextHolder;

/**
 * Created by gordon on 16/5/7.
 */
public class OrderView extends LinearLayout implements View.OnClickListener {
    private View rootView, plus, minus;
    private TextView nameText, priceText, sizeText, colorText, countText;
    private ImageView logo;

    private int count = 1;
    private String name, size, color, logoUrl;
    private float price;

    public OrderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rootView = LayoutInflater.from(ContextHolder.getInstance().getContext()).inflate(R.layout.layout_order_item, this);
        plus = rootView.findViewById(R.id.plus);
        minus = rootView.findViewById(R.id.minus);
        nameText = (TextView) rootView.findViewById(R.id.name);
        priceText = (TextView) rootView.findViewById(R.id.price);
        sizeText = (TextView) rootView.findViewById(R.id.size);
        colorText = (TextView) rootView.findViewById(R.id.color);
        countText = (TextView) rootView.findViewById(R.id.count);
        logo = (ImageView) rootView.findViewById(R.id.logo);
    }

    public void initialize(CartOrder model, PriceListener listener) {
        this.listener = listener;
        setCount(model.getAmount());
        setName(model.getName());
        setSize(model.getSize());
        setPrice(model.getPrice());
        setLogoUrl(model.getThumbnail());
        setColor(model.getColor());
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.minus) {
            setCount(count - 1);
        } else if (v.getId() == R.id.plus) {
            setCount(count + 1);
        }
    }

    public void setPrice(float price) {
        this.price = price;
        priceText.setText("$ " + price);
    }

    public void setName(String name) {
        this.name = name;
        nameText.setText(name);
    }

    public void setSize(String size) {
        this.size = size;
        sizeText.setText("Size: " + size);
    }

    public void setColor(String color) {
        this.color = color;
        colorText.setText("Color: " + color);
    }

    public void setCount(int count) {
        if (count < 1) return;
        this.count = count;
        countText.setText(String.valueOf(count));
        if (listener != null) listener.onPriceChanged();
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
        logo.setImageURI(Uri.parse(logoUrl));
    }

    public float getTotal() {
        return count * price;
    }

    private PriceListener listener;

    public interface PriceListener {
        void onPriceChanged();
    }
}
