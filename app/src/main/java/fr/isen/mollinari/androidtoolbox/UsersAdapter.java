package fr.isen.mollinari.androidtoolbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.isen.mollinari.androidtoolbox.model.User;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private final Context context;
    private List<User> usersList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, location, mail;
        private ImageView pic;

        private MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvName);
            location = view.findViewById(R.id.tvLocation);
            mail = view.findViewById(R.id.tvMail);
            pic = view.findViewById(R.id.ivPic);
        }
    }


    public UsersAdapter(List<User> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_web_service, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User randomUser = usersList.get(position);

        String name = capitalize(randomUser.getName().getFirst()) + " " + randomUser.getName().getLast().toUpperCase();
        String location = randomUser.getLocation().getStreet() + " " + randomUser.getLocation().getState() + " " + randomUser.getLocation().getCity();
        Picasso.with(context)
                .load(randomUser.getPicture().getMedium())
                .fit().centerInside()
                .transform(new RoundedTransformation(400, 0))
                .into(holder.pic);

        holder.name.setText(name);
        holder.location.setText(location);
        holder.mail.setText(randomUser.getEmail());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}