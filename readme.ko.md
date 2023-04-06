# Vite Thymeleaf Spring Boot Starter
Thymeleaf와 [Vite](https://vitejs.dev)를 사용할 수 있게 해주는 Spring Boot Starter.

## How it works
Vite사용 시 Vite에 의해 js 모듈에 대한 컴파일이 수행되며 이에 따라 정적 리소스들의 경로가 변경됨.
이를 해결하기 위해 Vite를 사용할 때 정적 리소스들의 경로를 Vite가 생성한 menifest.json을 참조해서 입려된 js, css의 경로를 컴파일된 결과물의 경로로 치환해주는 [Thymeleaf Vite Link Builder](https://github.com/WalkerKim/thymeleaf-vite-linkbuilder)를 사용할 수 있음.
본 프로젝트는 이를 위해 Vite를 사용할 때 정적 리소스들의 경로를 Vite에 의해 컴파일된 결과물의 경로로 치환해주는 LinkBuilder로 [Thymeleaf Vite Link Builder](https://github.com/WalkerKim/thymeleaf-vite-linkbuilder)를 자동으로 등록해주는 Spring Boot Starter를 제공.

## Version
| Vite Thymeleaf Starter version | Spring Boot version |
|--------------------------------|---------------------|
| 3.0.0                          | 3.x                 |
| 2.0.0 (not yet)                | 2.x                 |


## How to use
### Install vite at project root
```shell
$ npm create vite
? Project name: › vite-project
? Select a framework: › Vanilla
# Typescript를 사용하고 싶다면 ≤Typescript를 선택
? Select a variant: › JavaScript
# 생성된 디렉토리 내의 packege.json을 복사한 뒤 프로젝트 루트에 붙여넣고 해당 디렉토리는 삭제시킨다.
$ mv ./vite-project/package.json ./package.json
$ rm -rf ./vite-project
```
### Add Thymeleaf Spring Boot Starter dependency
 
**Maven**
```xml
        <dependency>
            <groupId>kim.figure</groupId>
            <artifactId>vite-thymeleaf-spring-boot-starter</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

**Gradle**
```yml
implementation 'kim.figure:vite-thymeleaf-spring-boot-starter:1.0-SNAPSHOT'
```
### create vite.config.js at project root

```shell
$ vi vite.config.js
```
vite.config.js
```js
import {defineConfig} from 'vite'
import path from 'node:path'
import glob from 'fast-glob';
import {normalizePath} from 'vite';

const projectRoot = __dirname
const sourceCodeDir = path.join(projectRoot, 'src/main/resources/static')
const root = sourceCodeDir
const outDir = path.relative(root, path.join(projectRoot, 'target/classes/static'))

const entrypoints = glob
    .sync(`${normalizePath(root)}/**/*`, {onlyFiles: true})
    .filter(i => i.endsWith('js') || i.endsWith('css'))
    .map((filename) => [path.relative(root, filename), filename])

export default defineConfig({
    root,
    server: {
        // vite dev server 작동시 Spring의 view로 접근하도록 Spring서버의 주소를 설정
        // /assets/js/,/assets/css/로 시작하는 요청은 vite dev server로 요청을 보내도록 설정
        // ts, scss등 자신의 환경에 맞게, vite 문법인 /@인 경우도 프록시 제외하고 vite dev서버를 거치도록 설정
        proxy: {
            '/': {
                target: 'http://localhost:8080',
                bypass: function (req, res, proxyOptions) {
                    if (req.url.startsWith('/assets/js/') || req.url.startsWith('/assets/css/') || req.url.startsWith('/@')) {
                        return req.url;
                    }
                    return null;
                }
            }
        },
    },
    appType: "custom",
    build: {
        // outDir에서 manifest.json을 생성.
        manifest: true,
        minify: false,
        outDir,
        assetsDir: "./",
        rollupOptions: {
            input: Object.fromEntries(entrypoints)
        }

    }
})
```
