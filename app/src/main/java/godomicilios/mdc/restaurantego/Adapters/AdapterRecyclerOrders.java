package godomicilios.mdc.restaurantego.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import godomicilios.mdc.restaurantego.Details;
import godomicilios.mdc.restaurantego.R;
import godomicilios.mdc.restaurantego.Utils.Connection;
import godomicilios.mdc.restaurantego.Utils.MaterialDialog;
import godomicilios.mdc.restaurantego.settings.order;
import godomicilios.mdc.restaurantego.settings.settings;

/**
 * Creado por Deimer el 18/08/17.
 */

public class AdapterRecyclerOrders extends RecyclerView.Adapter<AdapterRecyclerOrders.AdapterView> {

    private Context context;
    private List<order> orders = new ArrayList<>();
    private Connection connection;
    private MaterialDialog dialog;

    public AdapterRecyclerOrders(Context context, List<order> players) {
        this.context = context;
        this.orders = players;
    }

    @Override
    public AdapterView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_order, parent, false);
        return new AdapterView(layoutView);
    }

    @Override
    public void onBindViewHolder(final AdapterView holder, int position) {
        order order = orders.get(position);
        connection = new Connection(context);
        dialog = new MaterialDialog(context);
        //Functions
        animateCardPlayer(holder.card_new_order);
        setupFontLabels(holder);
        setupInfoOrder(holder, order);
        setupMethodPay(order.getMethod(), holder);
        setupStateOrder(order.getStatus(), holder);
        onclickDetail(order.getId(), position, holder);
        totalPrice(holder, order);
    }

    private void animateCardPlayer(CardView card_player){
        YoYo.with(Techniques.FadeInUp).duration(400).playOn(card_player);
    }

    private void setupFontLabels(AdapterView holder) {
        holder.metho.setTypeface(settings.fonts.typefaceR(context));
        holder.sale.setTypeface(settings.fonts.typefaceR(context));
        holder.price_living.setTypeface(settings.fonts.typefaceR(context));
        holder.price.setTypeface(settings.fonts.typefaceR(context));
        holder.user.setTypeface(settings.fonts.typefaceR(context));
        holder.date.setTypeface(settings.fonts.typefaceR(context));
        holder.address.setTypeface(settings.fonts.typefaceR(context));
        holder.lbl_title_order.setTypeface(settings.fonts.typefaceR(context));
        holder.lbl_number_order.setTypeface(settings.fonts.typefaceR(context));
        holder.lbl_title_state.setTypeface(settings.fonts.typefaceR(context));
        holder.lbl_title_method.setTypeface(settings.fonts.typefaceR(context));
        holder.lbl_title_sale.setTypeface(settings.fonts.typefaceR(context));
        holder.lbl_title_price_living.setTypeface(settings.fonts.typefaceR(context));
        holder.lbl_title_price.setTypeface(settings.fonts.typefaceR(context));
        holder.lbl_title_username.setTypeface(settings.fonts.typefaceR(context));
        holder.lbl_title_date.setTypeface(settings.fonts.typefaceR(context));
        holder.lbl_title_address.setTypeface(settings.fonts.typefaceR(context));
    }

    private void setupInfoOrder(AdapterView holder, order order) {
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        holder.lbl_number_order.setText(context.getString(R.string.lbl_numer) + order.getId());
        holder.sale.setText("$" + formatea.format(order.getSale()));
        holder.price_living.setText("$" + formatea.format(order.getPrice()));
        holder.user.setText(order.getUser());
        holder.date.setText(order.getDate());
        holder.address.setText(order.getAddress());
    }

    private void totalPrice(AdapterView holder, order order) {
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        float value = order.getPrice() + order.getSale();
        holder.price.setText("$" + formatea.format(value));
    }

    private void setupMethodPay(int method_id, AdapterView holder) {
        switch (method_id) {
            case 1:
                holder.metho.setText("EFECTIVO");
                break;
            case 2:
                holder.metho.setText("DATÁFONO");
                break;
            case 3:
                holder.metho.setText("OnLine");
                break;
        }
    }

    private void setupStateOrder(int state, AdapterView holder) {
        if (state == 1) {
            holder.lbl_status.setText("SE EFECTUÓ EL PAGO");
            holder.img_stat_order.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.puntovax3));
        } else {
            holder.lbl_status.setText("NO SE EFECTUÓ EL PAGO");
        }
    }

    private void onclickDetail(final int order_id, final int position, AdapterView holder) {
        holder.card_new_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connection.isOnline()) {
                    //openDetails(order_id, position);
                    dialog.toastInfo("Opción deshabilitada temporalmente!");
                } else {
                    dialog.toastWarning("Conexión de red inestable");
                }
            }
        });
    }

    private void openDetails(int order_id, int position) {
        Activity activity = (Activity)context;
        activity.startActivity(new Intent(context, Details.class)
                .putExtra("order_id", order_id)
                .putExtra("position", position)
        );
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class AdapterView extends RecyclerView.ViewHolder {
        @Bind(R.id.layout_new_order)LinearLayout layout_new_order;
        @Bind(R.id.card_new_order)CardView card_new_order;
        @Bind(R.id.lbl_status)TextView lbl_status;
        @Bind(R.id.img_stat_order)ImageView img_stat_order;
        @Bind(R.id.metho)TextView metho;
        @Bind(R.id.sale)TextView sale;
        @Bind(R.id.price_living)TextView price_living;
        @Bind(R.id.price)TextView price;
        @Bind(R.id.user)TextView user;
        @Bind(R.id.date)TextView date;
        @Bind(R.id.address)TextView address;
        @Bind(R.id.lbl_title_order) TextView lbl_title_order;
        @Bind(R.id.lbl_number_order) TextView lbl_number_order;
        @Bind(R.id.lbl_title_state) TextView lbl_title_state;
        @Bind(R.id.lbl_title_method) TextView lbl_title_method;
        @Bind(R.id.lbl_title_sale) TextView lbl_title_sale;
        @Bind(R.id.lbl_title_price_living)TextView lbl_title_price_living;
        @Bind(R.id.lbl_title_price) TextView lbl_title_price;
        @Bind(R.id.lbl_title_username) TextView lbl_title_username;
        @Bind(R.id.lbl_title_date) TextView lbl_title_date;
        @Bind(R.id.lbl_title_address) TextView lbl_title_address;
        private AdapterView(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
