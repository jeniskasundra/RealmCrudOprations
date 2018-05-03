package com.jenisk.realmcrudoprations.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jenisk.realmcrudoprations.MainActivity;
import com.jenisk.realmcrudoprations.R;
import com.jenisk.realmcrudoprations.ShowOnMapActivity;
import com.jenisk.realmcrudoprations.UpdateDirectoryActivity;
import com.jenisk.realmcrudoprations.database.RealmHelper;
import com.jenisk.realmcrudoprations.model.DirectoryData;
import com.jenisk.realmcrudoprations.model.DirectoryModel;

import java.io.File;
import java.util.ArrayList;
/**
 * Created by Jenis Kasundra on 03/05/2018.
 */
public class DirectoryListAdapter extends RecyclerView.Adapter<DirectoryListAdapter.ViewHolder>  {
    private ArrayList<DirectoryModel> directoryModelsArrayList;
    private  Context context;
    private RealmHelper helper;

    public DirectoryListAdapter(Context context, ArrayList<DirectoryModel> directoryModelsArrayList) {

        this.context=context;
        this.directoryModelsArrayList=directoryModelsArrayList;
        helper = new RealmHelper(context);
    }

    @NonNull
    @Override
    public DirectoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectoryListAdapter.ViewHolder holder, final int position) {
        final DirectoryModel directoryModel = directoryModelsArrayList.get(position);
        holder.tvName.setText(directoryModel.getName());
        holder.tvNumber.setText(directoryModel.getNumber());
        holder.tvAddress.setText(directoryModel.getAddress()+" ("+directoryModel.getLatitude()+","+directoryModel.getLongitude()+")");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ShowOnMapActivity.class);
                intent.putExtra("username",directoryModel.getName());
                intent.putExtra("latitude",directoryModel.getLatitude());
                intent.putExtra("longitude",directoryModel.getLongitude());
                context.startActivity(intent);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadUpdateDirectoryActivity(directoryModel.getId());
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDirectory(directoryModel.getId(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return directoryModelsArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvName, tvNumber,tvAddress;
        public Button btnEdit,btnDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvNameRaw);
            tvNumber = (TextView) itemView.findViewById(R.id.tvNumberRaw);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddressRaw);
            btnEdit=(Button)itemView.findViewById(R.id.btnEditRaw);
            btnDelete=(Button)itemView.findViewById(R.id.btnDeleteRaw);
        }
    }

    private void deleteDirectory(final int id,final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(
                context, R.style.AppAlertDialog);
        dialog.setTitle("Delete");
        dialog.setMessage("Are you sure you want to delete?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                helper.deleteDirectory(id);
                directoryModelsArrayList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Record deleted successfully.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private void loadUpdateDirectoryActivity(int id)
    {
        Intent intent=new Intent(context, UpdateDirectoryActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }
}