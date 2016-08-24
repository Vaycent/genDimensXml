# genDimensXml
------------------

You can use this java file to help u to generate dimens.xml automatically in your android project. Base on your values/dimens.xml in your android project, it will rise proportionately to the other size floder----"values-XX" and generate the dimens.xml

### Add genDimensXml to your project
------------------------
* Setup the  values/dimens.xml path in your android project
* You can use "baseOnDp" to declare the screen width which has been set for your dimes.xml 
* "dimensSet" is used to declare the folders in other size which u want to gen
* Run this genDimensXml.java and u will generate the dimen with other size in this android project

### Example
----------------
You can use these values to setup the path of your android project

```
private static final String basePath="/Users/vaycent/Documents/AndroidStudioWorkSpace";
private static final String projectName="/genDimensAdapter";
private static final String resourcePath="/app/src/main/res";
private static final String dimensPath="/values/dimens.xml";
```
To declare the scree width which you want to base on, and the floders which you want to gen

```
private static final int baseOnDp=360;

private static final String[] dimensSet={"sw360dp","sw411dp","sw480dp","sw576dp","sw600dp","sw800dp"};
	
```

### LICENSE
The source code is licensed under GPL v3. License is available [here](./LICENSE.txt)