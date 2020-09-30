package com.alejandro.android.femina;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.alejandro.android.femina.ui.ayuda.AyudaFragment;
import com.alejandro.android.femina.ui.contactos.ContactosFragment;
import com.alejandro.android.femina.ui.home.HomeFragment;
import com.alejandro.android.femina.ui.icono.IconoFragment;
import com.alejandro.android.femina.ui.perfil.PerfilFragment;
import com.alejandro.android.femina.ui.que_hacer.QueHacerFragment;
import com.alejandro.android.femina.ui.test_violencia.TestViolenciaFragment;
import com.alejandro.android.femina.ui.testimonios.TestimoniosFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
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

}