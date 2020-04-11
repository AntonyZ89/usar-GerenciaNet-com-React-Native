 Usar GerenciaNet com React Native
 --
 
#### Instalação

*Limpe o projeto e rebuild se necessário*

**1** - Adicione `compile 'br.com.gerencianet.mobile:gn-api-sdk-android:0.6'`

```
dependencies {
    ...
    compile 'br.com.gerencianet.mobile:gn-api-sdk-android:0.6' // <-
    ...
}
```

E se necessário adicione `multiDexEnabled true` em `defaultConfig`

```
defaultConfig {
    ...
    multiDexEnabled true // <-
    ...
}
```

**2** - Copie `GerenciaNetModule.java` e `GerenciaNetPackage.java` e que estão
neste repositório e cole-os no mesmo package que está o seu `MainApplication`


- **2.1** - Coloque o **código da conta** em `GerenciaNetModule:37`

**3** - Adicione o GerenciaNetPackage ao Package do React Native em `MainApplication`

```
@Override
protected List<ReactPackage> getPackages() {
  @SuppressWarnings("UnnecessaryLocalVariable")
  List<ReactPackage> packages = new PackageList(this).getPackages();
  // Packages that cannot be autolinked yet can be added manually here, for example:
  // packages.add(new MyReactNativePackage());
  packages.add(new GerenciaNetPackage()); // <-
  return packages;
}
```

#### Usando

O token é retornado de forma Assíncrona pela função que colocamos no último argumento.

```javascript
import React, {Component} from "react";
import {NativeModules} from "react-native";

const GerenciaNet = NativeModules.GerenciaNet;

export default class Exemplo extends Component {

    ...

    suaFuncao() {
        GerenciaNet.getPaymentToken( 'visa', '123', '1111222233334444', '01', '2030', result => {
            alert(JSON.stringify(result));
        });
    }

    ...

}
```
