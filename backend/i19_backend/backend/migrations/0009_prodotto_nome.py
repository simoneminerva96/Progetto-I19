# Generated by Django 2.2.2 on 2019-06-23 10:40

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('backend', '0008_auto_20190618_2225'),
    ]

    operations = [
        migrations.AddField(
            model_name='prodotto',
            name='nome',
            field=models.CharField(default='nome default', max_length=20),
            preserve_default=False,
        ),
    ]