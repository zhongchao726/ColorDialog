#ColorDialog

* Just a dialog
* Text Mode
* Image Mode
* Text & Image Mode


### 调用方式:

<pre><code>ColorDialog dialog = new ColorDialog(this);
        dialog.setTitle(getString(R.string.operation));
        dialog.setContentText(getString(R.string.content_text));
        dialog.setContentImage(getResources().getDrawable(R.mipmap.sample_img));
        dialog.setPositiveListener(getString(R.string.delete), new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                Toast.makeText(MainActivity.this, dialog.getPositiveText().toString(), Toast.LENGTH_SHORT).show();
            }
        })
        .setNegativeListener(getString(R.string.cancel), new ColorDialog.OnNegativeListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                Toast.makeText(MainActivity.this, dialog.getNegativeText().toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).show();
</code></pre>




### 效果图:
* Text Mode

![](https://github.com/andyxialm/ColorDialog/blob/master/art/Screenshot_text.png?raw=true)

* Image Mode

![](https://github.com/andyxialm/ColorDialog/blob/master/art/Screenshot_img.png?raw=true)

* Text Mode

![](https://github.com/andyxialm/ColorDialog/blob/master/art/Screenshot_imgtext.png?raw=true)


#License
<p>SymbolView is available under the MIT license.</p>
