# Bitcoin Widgets

![Bitcoin Widgets](https://repository-images.githubusercontent.com/516121902/7b13c221-db4b-42bc-9cc7-e45216f241d4)

See status of your [full node](https://bitcoin.org/en/full-node) and/or [mempool.space](https://mempool.space) Bitcoin explorer on your Android device.

## Installation

* Download [Bitcoin Widgets](https://play.google.com/store/apps/details?id=cz.yanas.bitcoin.widgets) from the Google Play Store.
* Download [Bitcoin Widgets APK](https://bitcoinwidgets.app/bitcoin-widgets-v1.3-universal.apk) directly from the project website. 
* Clone the repository and install the app manually via Android Studio.

## Configuration

Provide host and port of your full node in widget configuration.

* Public nodes are accessed directly via the [Bitcoin protocol](https://en.bitcoin.it/wiki/Protocol_documentation).
* Nodes running as Tor hidden services are accessed via the [Bitnodes API](https://bitnodes.io/api/).

If your node runs behind NAT, consider setting up a [reverse SSH tunnel](https://github.com/yanascz/bitcoind-tunnel).

Due to Android power restrictions, installed widgets are updated only once every 30 minutes.
If you want the widgets to be updated when screen is turned on, keep the app running in the background.

## Support

If you find Bitcoin Widgets useful, please consider supporting its maintenance by sending some sats to ⚡widgets@bitcoinwidgets.app.\
![Bitcoin Widgets LNURL-pay QR code](https://bitcoinwidgets.app/ln/pay/widgets/qr-code)
