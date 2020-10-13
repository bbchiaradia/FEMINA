package com.alejandro.android.femina.Main;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;
import com.alejandro.android.femina.Adaptadores.AdapterVideos;
import com.alejandro.android.femina.Entidades.Videos;
import com.alejandro.android.femina.Fragments.ayuda.AyudaFragment;
import com.alejandro.android.femina.Fragments.contactos.Agregar_editar.ContactosAEFragment;
import com.alejandro.android.femina.Fragments.contactos.Principal.ContactosFragment;
import com.alejandro.android.femina.Fragments.contactos.Seleccion.ContactosSeleccionFragment;
import com.alejandro.android.femina.Fragments.home.HomeFragment;
import com.alejandro.android.femina.Fragments.icono.IconoFragment;
import com.alejandro.android.femina.Fragments.perfil.PerfilFragment;
import com.alejandro.android.femina.Fragments.que_hacer.Principal.QueHacerFragment;
import com.alejandro.android.femina.Fragments.test_violencia.Principal.TestViolenciaFragment;
import com.alejandro.android.femina.Fragments.testimonios.Principal.TestimoniosFragment;
import com.alejandro.android.femina.Pantallas_exteriores.Ingresar;
import com.alejandro.android.femina.R;
import com.google.android.material.navigation.NavigationView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private BottomBar bottomBar;
    private static final int INTERVALO = 2000;
    private long tiempoPrimerClick;
    private AdapterVideos adapter_video;


     private List<Videos> youtubeVideoList;
     protected List<Videos> VideoListFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Fragment fragment = new HomeFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.content_main, fragment).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        navigationView.setItemIconTintList(null);
        // barra con los botones de inicio , llamadas de emergencia , sms de ayuda
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_ini) {
                    // The tab with id R.id.tab_calls was selected,
                    // change your content accordingly.
                    Toast.makeText(getApplicationContext(), "Estoy en inicio", Toast.LENGTH_SHORT).show();
                    //textView.setText("Your Calls");
                } else if (tabId == R.id.tab_call911) {
                    // change your content accordingly.
                    String phone_911 = "tel:611";
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(phone_911));
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(intent);

                } else if (tabId == R.id.tab_call144) {
                    // change your content accordingly.
                    String phone_144 = "tel:114";
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(phone_144));
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(intent);

                } else if (tabId == R.id.tab_sms) {
                    // The tab with id R.id.tab_chats was selected,
                    // change your content accordingly.
                    Toast.makeText(getApplicationContext(),"envio sms",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
          MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //RECUPERA EL ITEM QUE SE ESTA PRESIONANDO
        switch (item.getItemId()){
            case R.id.action_cerrarSesion:
                logOut();
                return true;
            case R.id.action_finalizar:
                finalizarApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void logOut(){
        Intent intent = new Intent(this, Ingresar.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
       Process.killProcess(Process.myPid());
        super.onDestroy();
    }

    private void finalizarApp(){
        finish();
        System.runFinalization();
        MainActivity.this.finish();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Log.d("Navigation", "Item selected : " + menuItem.getItemId());

        if (id == R.id.nav_mis_datos) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new PerfilFragment()).commit();
        }else if (id == R.id.nav_home) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new HomeFragment()).commit();
        }else if (id == R.id.nav_ayuda) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new AyudaFragment()).commit();
        }else if (id == R.id.nav_contactos_emergencia) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new ContactosFragment()).commit();
        } else if (id == R.id.nav_crear_icono) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new IconoFragment()).commit();
        } else if (id == R.id.nav_que_hacer) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new QueHacerFragment()).commit();
        } else if (id == R.id.nav_test_violencia) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new TestViolenciaFragment()).commit();
        } else if (id == R.id.nav_testimonios) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new TestimoniosFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed(){

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_main);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(currentFragment instanceof ContactosAEFragment){
            fragmentManager.beginTransaction().replace(R.id.content_main, new ContactosFragment()).commit();
        }
        else if(currentFragment instanceof ContactosSeleccionFragment) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new ContactosAEFragment()).commit();
        }else if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            } else {
                Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
            }
            tiempoPrimerClick = System.currentTimeMillis();
        }

        }