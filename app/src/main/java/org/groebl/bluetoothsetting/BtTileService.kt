package org.groebl.bluetoothsetting

import android.bluetooth.BluetoothAdapter
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService


class BtTileService : TileService() {

    private val adapter = BluetoothAdapter.getDefaultAdapter()

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * Called when the tile is added to the quick settings from the edit interface by the user. If
     * you keep track of added tiles, override this and update it.
     * <p>
     * Return either TILE_MODE_ACTIVE or TILE_MODE_PASSIVE depending on your requirements
     */
    override fun onTileAdded() {
        super.onTileAdded()
        qsTile.state = Tile.STATE_INACTIVE
        qsTile.label = getString(R.string.bt_tile_title)
        qsTile.updateTile()
    }

    /**
     * Called when the tile is removed from the quick settings using the edit interface. Similarly
     * to onTileAdded, override this and update your tracking here if you need to
     */
    override fun onTileRemoved() {
        super.onTileRemoved()
    }

    /**
     * Called when the tile is brought into the listening state. Update it with your icon and title
     * here, using getQsTile to get the tile (see below)
     */
    override fun onStartListening() {
        super.onStartListening()
        if(adapter == null) {
            qsTile.state = Tile.STATE_UNAVAILABLE
            qsTile.label = getString(R.string.bt_not_available)
        }
        else if(!adapter.isEnabled) {
            qsTile.state = Tile.STATE_UNAVAILABLE
            qsTile.label = getString(R.string.bt_off)
        } else {
            when (adapter.scanMode) {
                BluetoothAdapter.SCAN_MODE_CONNECTABLE -> {
                    qsTile.state = Tile.STATE_INACTIVE
                    qsTile.label = getString(R.string.bt_not_visible)
                }
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE -> {
                    qsTile.state = Tile.STATE_ACTIVE
                    qsTile.label = getString(R.string.bt_visible)
                }
                else -> {
                    qsTile.state = Tile.STATE_ACTIVE
                    qsTile.label = getString(R.string.bt_unknown)
                }
            }

        }
        qsTile.updateTile()
    }

    /**
     * Called when the tile is brought out of the listening state. This represents when getQsTile
     * will now return null.
     */
    override fun onStopListening() {
        super.onStopListening()
    }

    /**
     * Called when the tile is clicked. Can be called multiple times in short succession, so double
     * click (and beyond) is possible
     */
    override fun onClick() {
        super.onClick()

        when (adapter?.scanMode) {
            BluetoothAdapter.SCAN_MODE_CONNECTABLE -> {
                MainActivity.setAdapterScanMode.invoke(adapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE, 1)
                //Set to visible
                qsTile.state = Tile.STATE_ACTIVE
                qsTile.label = getString(R.string.bt_visible)
            }
            else -> {
                MainActivity.setAdapterScanMode.invoke(adapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE, 1)
                //Set to not visible
                qsTile.state = Tile.STATE_INACTIVE
                qsTile.label = getString(R.string.bt_not_visible)
            }
        }

        qsTile.updateTile()
    }
}