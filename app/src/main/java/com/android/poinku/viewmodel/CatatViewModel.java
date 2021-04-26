package com.android.poinku.viewmodel;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.response.BaseResponse;
import com.android.poinku.api.response.JenisResponse;
import com.android.poinku.api.response.LingkupResponse;
import com.android.poinku.api.response.PeranResponse;
import com.android.poinku.api.response.TugasKhususResponse;
import com.android.poinku.repository.OnlineRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CatatViewModel extends AndroidViewModel {
    private final OnlineRepository onlineRepository;
    private Context context;

    public CatatViewModel(@NonNull Application application) {
        super(application);
        onlineRepository = new OnlineRepository();
        context = application.getApplicationContext();
    }

    public MutableLiveData<JenisResponse> getJenis() {
        return onlineRepository.getJenis();
    }

    public MutableLiveData<LingkupResponse> getLingkup() {
        return onlineRepository.getLingkup();
    }

    public MutableLiveData<PeranResponse> getPeran() {
        return onlineRepository.getPeran();
    }

    public MutableLiveData<TugasKhususResponse> postTugasKhusus(String nrp, String jenis, String lingkup, String peran, String judul, String tanggal) {
        return onlineRepository.postTugasKhusus(
                nrp,
                jenis,
                lingkup,
                peran,
                judul,
                tanggal
        );
    }

    public MutableLiveData<BaseResponse> postKonten(String idTugasKhusus, String media, String jenis) {
        return onlineRepository.postKonten(
                idTugasKhusus,
                media,
                jenis
        );
    }

    public MutableLiveData<BaseResponse> postKegiatan(String idTugasKhusus, String keterangan) {
        return onlineRepository.postKegiatan(
                idTugasKhusus,
                keterangan
        );
    }

    public MutableLiveData<BaseResponse> postBuktiKonten(String idTugasKhusus, String bukti) {
        return onlineRepository.postBuktiKonten(
                idTugasKhusus,
                bukti
        );
    }

    public MutableLiveData<BaseResponse> postBuktiKegiatan(String idTugasKhusus, String nrp, String namaJenis, Uri file) {
        RequestBody idTugasKhusus_ = RequestBody.create(MediaType.parse("text/plain"), idTugasKhusus);
        RequestBody nrp_ = RequestBody.create(MediaType.parse("text/plain"), nrp);
        RequestBody namaJenis_ = RequestBody.create(MediaType.parse("text/plain"), namaJenis);
        return onlineRepository.postBuktiKegiatan(
                idTugasKhusus_,
                nrp_,
                namaJenis_,
                compressFile(file, "file")
        );
    }

    private File createTempFile(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() +".JPEG");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        //write the bytes in file
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(byteArray);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private MultipartBody.Part compressFile(Uri uri, String path) {
        File file = new File(uri.getPath());
        try {
            File fileCompress = new Compressor(context)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .compressToFile(file);
            File file1 = createTempFile(Uri.fromFile(fileCompress.getAbsoluteFile()));
            Log.e("path", file1.getAbsolutePath());
            return MultipartBody.Part.createFormData(path, file1.getName(), RequestBody.create(MediaType.parse("image/*"), file1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
