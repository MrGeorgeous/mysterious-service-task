package ru.gsemenov.mysteriousservice;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

public class MysteriousService extends Service implements SensorEventListener {

    /**
     * Порог в люксах, считающийся наступлением темноты
     */
    public static final float DARKNESS_IN_LX = 100.0f;

    /**
     * Проигрывать звук не чаще, чем это значение
     */
    public static final long PLAY_TIMEOUT_IN_MS = 30_000L;

    /* -----------------------------------------------------------------*/

    /**
     * Служебный класс - менеджер датчиков
     */
    private SensorManager sensorManager;

    /**
     * Датчик света
     */
    private Sensor lightSensor;

    /**
     * Временная метка Unix в миллисекундах последнего проигрывания звука
     * Получить текущую метку времени можно с помощью System.currentTimeInMillis();
     */
    private long lastPlayTime = 0L;

    /**
     * Текущее состояние звука - проигрывается он или нет
     */
    private volatile AtomicBoolean isCurrentlyPlaying = new AtomicBoolean(false);

    /**
     * Установить флажок, что звук сейчас проигрывается (да или нет)
     */
    private void setIsCurrentlyPlaying(boolean v) {
        this.isCurrentlyPlaying.set(v);
    }

    /**
     * Получить текущий флажок: проигрывается ли звук
     */
    private boolean getIsCurrentlyPlaying() {
        return this.isCurrentlyPlaying.get();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // TODO: вывести тост с текстом "Мистический сервис запущен"
        return START_NOT_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {

            float lightness = event.values[0];

            // TODO: если lightness ниже порога темноты DARKNESS_IN_LX, звук сейчас не играет,
            //  а последний раз звук проигрывался более, чем PLAY_TIMEOUT_IN_MS,
            //  проиграть звук play()

        }

    }

    /**
     * Проигрывает мистический звук
     */
    private void play() {
        // TODO: залогируйте очередное проигрывание звука с помощью
        // Log.d("MYSTERY", "Проигрываем звук при освещенности " + lightness);
        // TODO: поставить флажок isCurrentlyPlaying
        // TODO: обновить последнее время запуска звука в lastPlayTime
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.illuminati);
        mp.setVolume(0.5f, 0.5f);
        mp.start();
        mp.setOnCompletionListener(mp1 -> {
            // Этот код выполняется по завершении проигрывания звука
            // Нам необходимо сначала отпустить ресурс проигрывателя
            mp1.release();
            // TODO: а теперь вам необходимо снять флажок isCurrentlyPlaying
        });
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Этот сервис не поддерживает запуск с помощью bind");
    }

    @Override
    public void onDestroy() {
        sensorManager.unregisterListener(this, lightSensor);
        // TODO: вывести тост с текстом "Мистический сервис остановлен"
    }

}