# Cursos UNIR

Aplicativo Android em Java, com interface XML e três Activities. Contém os 45 cursos dos arquivos fornecidos, sem alterar nomes, campi, graus, turnos, descrições ou sites. Cinco URLs de imagens foram substituídas para melhorar sua relação com os cursos. Os comentários das classes fornecidas foram removidos conforme o pedido.

## Abrir e executar

1. Extraia o ZIP.
2. No Android Studio, use **Open** e selecione a pasta **CursosUNIR**, que contém `settings.gradle`. Não selecione somente a pasta `app`.
3. Utilize Android Studio Panda 3 (2025.3.3 Patch 1) ou mais recente compatível com AGP 9.1.1.
4. Em **Settings > Build, Execution, Deployment > Build Tools > Gradle**, escolha um Gradle JDK 17 ou superior compatível. Não precisa instalar Gradle separadamente: o wrapper está incluído.
5. No **SDK Manager**, instale a plataforma **Android API 37** e os SDK Build Tools solicitados pelo Gradle. O AGP 9.1.1 usa Build Tools 36.0.0 por padrão; compileSdk continua sendo 37.
6. Aguarde a sincronização. A primeira sincronização precisa de Internet para baixar Gradle e dependências.
7. Crie um emulador com API 31 ou superior ou conecte um aparelho com Android 12 ou superior.
8. Selecione o módulo `app` e clique em **Run**.

O Android Studio configura o caminho local do SDK. Não foi incluído um `local.properties` com caminhos de outra máquina.

Para gerar o APK pelo terminal no Windows, dentro da pasta do projeto:

```bat
gradlew.bat assembleDebug
```

No Linux/macOS:

```bash
./gradlew assembleDebug
```

O APK, após compilação bem-sucedida, fica em `app/build/outputs/apk/debug/app-debug.apk`.

## Configuração

| Item | Valor |
| --- | --- |
| Package e applicationId | com.unir.cursosunir |
| Linguagem dos fontes | Java |
| Interface | XML |
| minSdk | 31 |
| targetSdk | 37 |
| compileSdk | 37 |
| Android Gradle Plugin | 9.1.1 |
| Gradle Wrapper | 9.3.1 |
| Compatibilidade do código Java | 17 |

Dependências: AppCompat 1.7.1, Activity 1.10.1, Core 1.16.0, RecyclerView 1.4.0 e Glide 4.16.0. Activity/Core permitem ajustar o conteúdo às barras do sistema. Não há fontes Kotlin, Compose, banco de dados ou API de cursos. Bibliotecas AndroidX podem ter dependências internas próprias.

Referências de compatibilidade:

- https://developer.android.com/build/releases/agp-9-1-0-release-notes
- https://developer.android.com/build/releases/about-agp

## Arquivos principais

```text
CursosUNIR/
  settings.gradle
  build.gradle
  gradle.properties
  gradlew
  gradlew.bat
  gradle/wrapper/gradle-wrapper.jar
  gradle/wrapper/gradle-wrapper.properties
  app/build.gradle
  app/src/main/AndroidManifest.xml
  app/src/main/java/com/unir/cursosunir/
    MainActivity.java
    ListaCursosActivity.java
    DetalhesCursoActivity.java
    CursoAdapter.java
    Curso.java
    CursosData.java
  app/src/main/res/layout/
    activity_main.xml
    activity_lista_cursos.xml
    activity_detalhes_curso.xml
    item_curso.xml
  app/src/main/res/drawable/
    fundo_card.xml
    fundo_foto.xml
    ic_educacao.xml
    ic_voltar.xml
    ic_site.xml
    ic_compartilhar.xml
    ic_launcher_foreground.xml
  app/src/main/res/drawable-nodpi/
    logo_unir.png
    fachada_unir.jpg
  app/src/main/res/mipmap-anydpi-v26/
    ic_launcher.xml
    ic_launcher_round.xml
  app/src/main/res/values/
    colors.xml
    strings.xml
    themes.xml
```

## Visual do aplicativo

Interface acadêmica simples, com azul inspirado na logo enviada, cabeçalho com a marca da UNIR e foto da fachada sem efeitos por cima. O Spinner e os botões usam a aparência convencional do Android. A lista tem fotos, textos e uma seta; os detalhes apresentam as informações com alguns ícones flat.

Os cantos são levemente arredondados, sem etiquetas de grau, cartões de filtros ou botão contornado. A logo e a fachada originais foram mantidas. As três Activities e todas as funções continuam presentes.

As imagens de Ciências Biológicas, Letras Libras, Psicologia, Engenharia Ambiental e Sanitária e Engenharia de Pesca foram substituídas por fotos relacionadas aos cursos. A associação completa está em `IMAGENS.md`.

## Como o aplicativo funciona

### 1. Envio dos filtros

A MainActivity percorre `CursosData.getCursos()` e adiciona cada campus ao Spinner somente se ele ainda não estiver na lista. A primeira opção é “Todos os campi”.

Ao tocar em VER CURSOS, lê o campus, o RadioButton selecionado e o CheckBox. Cria uma Intent explícita para ListaCursosActivity e envia os extras `campus`, `grau` e `noturno`. Os dois primeiros são textos e o último é booleano. Os widgets mantêm a seleção na tela; o botão consulta seus valores, sem precisar de eventos extras para cada alteração.

### 2. Filtragem

ListaCursosActivity recupera os extras de `getIntent()`. Caso um texto esteja ausente, usa a opção “Todos”.

O `for` percorre os cursos originais. Um curso entra em `cursosFiltrados` apenas se as três condições forem verdadeiras: campus correspondente ou todos os campi; grau correspondente ou todos os graus; turno Noturno se o CheckBox foi marcado. Desmarcar a opção permite qualquer turno, inclusive Integral.

A lista é calculada a cada abertura da Activity. Não existem listas fixas para as combinações. Quando a combinação não tem resultados, uma mensagem aparece e o botão de voltar continua disponível.

### 3. Adapter e ViewHolder

`CursoAdapter` recebe o ArrayList filtrado. `getItemCount()` informa quantos cursos há. `onCreateViewHolder()` cria um item a partir de `item_curso.xml`. O `CursoViewHolder` guarda as referências da imagem e dos três TextViews.

`onBindViewHolder()` recebe a posição, obtém o curso dessa posição e preenche os componentes. O RecyclerView reutiliza os itens durante a rolagem. O LinearLayoutManager organiza a lista verticalmente.

O evento de clique é atualizado a cada associação do item, usando o curso correspondente, e abre a tela de detalhes.

### 4. Passagem para os detalhes

O Adapter cria uma Intent explícita para DetalhesCursoActivity. Envia os extras `nome`, `campus`, `grau`, `turno`, `descricao`, `imagem` e `site`, todos do objeto selecionado.

DetalhesCursoActivity recupera esses sete textos com `getStringExtra()`, exibe os dados e verifica as informações obrigatórias. Se dados essenciais estiverem ausentes, exibe um Toast e volta à tela anterior.

### 5. Glide e imagens

O Adapter chama `Glide.with(holder.itemView).load(cursoSelecionado.getImagem())`. A tela de detalhes usa `Glide.with(this).load(imagem)`. `into()` indica qual ImageView recebe a imagem; `centerCrop()` ajusta a foto ao espaço reservado.

A foto é obtida da URL do curso, com a permissão INTERNET do Manifest. O ícone de educação é apenas o placeholder e o substituto em caso de erro. As fotos não foram adicionadas à pasta drawable. Se o dispositivo estiver sem rede, o substituto pode aparecer; os dados e filtros continuam locais.

### 6. Intents implícitas

ACESSAR SITE usa `Intent.ACTION_VIEW` com `Uri.parse(site)`. Verifica se há um endereço HTTP/HTTPS com host e solicita ao Android um aplicativo que abra o link. Caso não haja aplicativo compatível, exibe um Toast.

COMPARTILHAR usa `Intent.ACTION_SEND`, tipo `text/plain` e `Intent.EXTRA_TEXT`. O texto contém nome, campus e link. `Intent.createChooser()` abre o seletor de aplicativos.

As Intents de navegação são explícitas porque informam a classe de destino. As de site e compartilhamento são implícitas porque informam a ação, e o Android escolhe os aplicativos compatíveis.

### 7. Retorno e barras do sistema

Os botões de voltar chamam `finish()`, retornando à Activity anterior. O botão/gesto de voltar do sistema mantém o comportamento normal das Activities.

As três telas usam EdgeToEdge e aplicam os insets das barras e recortes da tela ao layout principal. A tela inicial e os detalhes têm ScrollView. Os textos podem crescer sem depender de uma altura fixa para cada informação.

## Verificações realizadas

- Sintaxe dos seis arquivos Java analisada pelo compilador Java 17: sem erros de sintaxe.
- `Curso` e `CursosData` compilados no Java 17 e utilizados nas verificações locais.
- XMLs analisados e referências aos recursos e IDs verificadas.
- Todos os textos e sites das classes fornecidas comparados com os originais: preservados. Apenas cinco URLs de imagens foram substituídas nesta revisão.
- Filtragem extraída da própria ListaCursosActivity e executada com os dados reais: 45 cursos no total, 27 bacharelados, 18 licenciaturas e 8 resultados para Porto Velho + Licenciatura + Noturno.
- Resultado vazio conferido com Presidente Médici + Licenciatura + Noturno.
- Combinações de campus, grau e opção noturna verificadas sobre os dados reais.
- As 34 imagens originais foram baixadas temporariamente e conferidas visualmente. As cinco substituições também foram baixadas e conferidas: todas são imagens JPEG válidas. As fotos de cursos continuam sendo carregadas por URL com Glide; somente a logo e a fachada fornecidas ficam no projeto. Ainda é necessário conferir o carregamento no emulador.

**A compilação Android completa e a execução no emulador ainda precisam ser realizadas no Android Studio.** Não havia Android SDK nem emulador neste ambiente. A tentativa de iniciar o Gradle Wrapper foi bloqueada pela conexão de rede do processo Java ao baixar a distribuição. Não foi gerado nem testado um APK. A validação de sintaxe não confirma sozinha a compilação com as bibliotecas Android.

## Conferência no emulador

1. Abra sem mudar filtros e confira os 45 cursos.
2. Selecione Porto Velho, Licenciatura e Somente cursos noturnos. Confira 8 resultados: Artes Visuais, Física, Geografia, História, Letras Libras, Matemática, Música e Química.
3. Selecione Presidente Médici, Licenciatura e Somente cursos noturnos. Confira a mensagem de lista vazia.
4. Abra um curso da lista, confira os dados e o carregamento da foto na lista e nos detalhes.
5. Toque em ACESSAR SITE e confira o navegador. A disponibilidade dos sites depende dos servidores externos; seus endereços originais foram preservados.
6. Toque em COMPARTILHAR e confira o nome, campus e link no texto enviado.
7. Confira os botões de retorno e o gesto do sistema.
8. Confira as telas em API 31 e API 37, incluindo rolagem, texto ampliado e barras do sistema.
