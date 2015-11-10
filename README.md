#ColorDialog

* Just a dialog
* Text Mode
* Image Mode
* Text & Image Mode
* Thanks to [@Diego Faria](https://dribbble.com/shots/1626595-Feedback-dialogs-collection-FREE-PSD "Title") from Dribbble. 


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

<pre><code>new PromptDialog(this).setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
            .setTitleText("Success").setContentText("Your info text goes here. Loremipsum dolor sit amet, consecteturn adipisicing elit, sed do eiusmod.")
            .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();
                }
            }).show();</code></pre>




### 效果图:
* Text Mode

![](https://github.com/andyxialm/ColorDialog/blob/master/art/Screenshot_text.png?raw=true)

* Image Mode

![](https://github.com/andyxialm/ColorDialog/blob/master/art/Screenshot_img.png?raw=true)

* Text Mode

![](https://github.com/andyxialm/ColorDialog/blob/master/art/Screenshot_imgtext.png?raw=true)


#License
<p>ColorDialog is available under the MIT license.</p>
