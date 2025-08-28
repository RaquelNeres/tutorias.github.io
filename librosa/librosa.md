# Tutorial Completo da Biblioteca LibROSA

## Índice
1. [Introdução](#introdução)
2. [Instalação](#instalação)
3. [Primeiros Passos](#primeiros-passos)
4. [Carregando Arquivos de Áudio](#carregando-arquivos-de-áudio)
5. [Inspeção e Visualização](#inspeção-e-visualização)
6. [Extração de Características](#extração-de-características)
7. [Análise Espectral](#análise-espectral)
8. [Manipulação de Áudio](#manipulação-de-áudio)
9. [Análise de Ritmo e Tempo](#análise-de-ritmo-e-tempo)
10. [Casos de Uso Avançados](#casos-de-uso-avançados)
11. [Integração com Machine Learning](#integração-com-machine-learning)
12. [Melhores Práticas](#melhores-práticas)

## Introdução

LibROSA é um pacote Python para análise de música e áudio. Desenvolvida por Brian McFee e o grupo de pesquisa LabROSA da Universidade de Columbia, esta biblioteca fornece uma ampla gama de funções e ferramentas para tarefas como:

- Carregamento de arquivos de áudio
- Computação de espectrogramas
- Extração de características
- Visualização de dados de áudio
- Análise musical e de fala
- Processamento de sinais de áudio

A LibROSA é amplamente utilizada em projetos de:
- Reconhecimento de fala
- Classificação de gêneros musicais
- Reconhecimento de instrumentos
- Análise de sentimentos em áudio
- Sistemas de recomendação musical

## Instalação

### Usando pip
```bash
pip install librosa
```

### Usando conda
```bash
conda install -c conda-forge librosa
```

### Dependências Opcionais
Para funcionalidades adicionais, instale:
```bash
# Para visualização avançada
pip install librosa[display]

# Para processamento de áudio adicional
pip install librosa[extra]
```

### Verificando a Instalação
```python
import librosa
print(librosa.__version__)
```

## Primeiros Passos

### Importações Básicas
```python
import librosa
import librosa.display
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
```

### Estrutura da Biblioteca
O pacote librosa está estruturado como uma coleção de submódulos:

- `librosa.core`: Funcionalidades centrais
- `librosa.feature`: Extração de características
- `librosa.display`: Visualização
- `librosa.beat`: Funções para estimar tempo e detectar eventos de batida
- `librosa.onset`: Detecção de início de notas
- `librosa.effects`: Efeitos de áudio
- `librosa.util`: Utilitários gerais

## Carregando Arquivos de Áudio

### Carregamento Básico
```python
# Carrega um arquivo de áudio
audio_data, sampling_rate = librosa.load('audio_file.wav')

# Com taxa de amostragem específica
audio_data, sr = librosa.load('audio_file.wav', sr=22050)

# Mantendo a taxa de amostragem original
audio_data, sr = librosa.load('audio_file.wav', sr=None)
```

### Carregando Apenas uma Porção
```python
# Carrega apenas os primeiros 30 segundos
audio_data, sr = librosa.load('audio_file.wav', duration=30.0)

# Carrega a partir do segundo 10 até o 40
audio_data, sr = librosa.load('audio_file.wav', offset=10.0, duration=30.0)
```

### Formatos Suportados
A LibROSA suporta diversos formatos de áudio:
- WAV
- MP3
- FLAC
- OGG
- M4A
- E outros através do ffmpeg

## Inspeção e Visualização

### Propriedades Básicas do Áudio
```python
# Duração em segundos
duration = librosa.get_duration(y=audio_data, sr=sr)
print(f"Duração: {duration:.2f} segundos")

# Número de amostras
n_samples = len(audio_data)
print(f"Número de amostras: {n_samples}")

# Taxa de amostragem
print(f"Taxa de amostragem: {sr} Hz")
```

### Visualizando a Forma de Onda
```python
plt.figure(figsize=(12, 4))
librosa.display.waveshow(audio_data, sr=sr)
plt.title('Forma de Onda')
plt.xlabel('Tempo (s)')
plt.ylabel('Amplitude')
plt.show()
```

### Visualizando o Espectrograma
```python
# Calcular espectrograma
D = librosa.amplitude_to_db(np.abs(librosa.stft(audio_data)), ref=np.max)

# Plotar espectrograma
plt.figure(figsize=(12, 8))
librosa.display.specshow(D, sr=sr, x_axis='time', y_axis='hz')
plt.colorbar(format='%+2.0f dB')
plt.title('Espectrograma')
plt.show()
```

## Extração de Características

### MFCC (Mel Frequency Cepstral Coefficients)
MFCC são características muito comumente usadas para análise de fala/música:

```python
# Extrair 13 coeficientes MFCC
mfcc = librosa.feature.mfcc(y=audio_data, sr=sr, n_mfcc=13)
print(f"Shape do MFCC: {mfcc.shape}")

# Visualizar MFCC
plt.figure(figsize=(12, 4))
librosa.display.specshow(mfcc, sr=sr, x_axis='time')
plt.colorbar()
plt.title('Coeficientes MFCC')
plt.ylabel('Coeficiente MFCC')
plt.show()
```

### Características de Croma
As características de croma visam capturar a progressão harmônica de um sinal de áudio:

```python
# Extrair características de croma
chroma = librosa.feature.chroma_cqt(y=audio_data, sr=sr)

plt.figure(figsize=(12, 4))
librosa.display.specshow(chroma, sr=sr, x_axis='time', y_axis='chroma')
plt.colorbar()
plt.title('Características de Croma')
plt.show()
```

### Contraste Espectral
As características de contraste espectral destacam regiões de alta atividade espectral:

```python
# Extrair contraste espectral
contrast = librosa.feature.spectral_contrast(y=audio_data, sr=sr)

plt.figure(figsize=(12, 4))
librosa.display.specshow(contrast, sr=sr, x_axis='time')
plt.colorbar()
plt.title('Contraste Espectral')
plt.show()
```

### Tonnetz
As características tonnetz mapeiam as características de croma em um espaço de seis dimensões:

```python
# Extrair características tonnetz
tonnetz = librosa.feature.tonnetz(y=audio_data, sr=sr)

plt.figure(figsize=(12, 4))
librosa.display.specshow(tonnetz, sr=sr, x_axis='time')
plt.colorbar()
plt.title('Tonnetz')
plt.show()
```

### Outras Características Importantes
```python
# Centroide espectral
spectral_centroids = librosa.feature.spectral_centroid(y=audio_data, sr=sr)[0]

# Rolloff espectral
spectral_rolloff = librosa.feature.spectral_rolloff(y=audio_data, sr=sr)[0]

# Largura de banda espectral
spectral_bandwidth = librosa.feature.spectral_bandwidth(y=audio_data, sr=sr)[0]

# Zero crossing rate
zcr = librosa.feature.zero_crossing_rate(audio_data)[0]

# RMS Energy
rms = librosa.feature.rms(y=audio_data)[0]
```

## Análise Espectral

### Transformada de Fourier de Curto Tempo (STFT)
```python
# Calcular STFT
stft = librosa.stft(audio_data)
magnitude = np.abs(stft)
phase = np.angle(stft)

# Converter para dB
magnitude_db = librosa.amplitude_to_db(magnitude, ref=np.max)

# Visualizar
plt.figure(figsize=(12, 8))
librosa.display.specshow(magnitude_db, sr=sr, x_axis='time', y_axis='hz')
plt.colorbar(format='%+2.0f dB')
plt.title('STFT - Magnitude')
plt.show()
```

### Transformada Constante Q (CQT)
```python
# Calcular CQT (melhor para análise musical)
cqt = librosa.cqt(audio_data, sr=sr)
cqt_db = librosa.amplitude_to_db(np.abs(cqt), ref=np.max)

plt.figure(figsize=(12, 8))
librosa.display.specshow(cqt_db, sr=sr, x_axis='time', y_axis='cqt_note')
plt.colorbar(format='%+2.0f dB')
plt.title('Transformada Constante Q')
plt.show()
```

### Espectrograma Mel
```python
# Calcular espectrograma mel
mel_spec = librosa.feature.melspectrogram(y=audio_data, sr=sr, n_mels=128)
mel_spec_db = librosa.power_to_db(mel_spec, ref=np.max)

plt.figure(figsize=(12, 8))
librosa.display.specshow(mel_spec_db, sr=sr, x_axis='time', y_axis='mel')
plt.colorbar(format='%+2.0f dB')
plt.title('Espectrograma Mel')
plt.show()
```

## Manipulação de Áudio

### Reamostragem
Você pode reamostrar um sinal de áudio para uma frequência diferente:

```python
# Reamostrar para 16kHz
new_sr = 16000
audio_resampled = librosa.resample(audio_data, orig_sr=sr, target_sr=new_sr)
```

### Recorte (Trimming)
Você pode recortar um sinal de áudio para um segmento mais curto:

```python
# Remover silêncio do início e fim
audio_trimmed, _ = librosa.effects.trim(audio_data, top_db=20)
```

### Mudança de Tom (Pitch Shifting)
O tom do áudio pode ser alterado:

```python
# Aumentar o tom em 2 semitons
audio_pitched = librosa.effects.pitch_shift(audio_data, sr=sr, n_steps=2)

# Diminuir o tom em 3 semitons
audio_pitched_down = librosa.effects.pitch_shift(audio_data, sr=sr, n_steps=-3)
```

### Alteração de Tempo
A velocidade e o tempo do áudio podem ser alterados:

```python
# Acelerar o áudio (fator 1.5)
audio_faster = librosa.effects.time_stretch(audio_data, rate=1.5)

# Desacelerar o áudio (fator 0.8)
audio_slower = librosa.effects.time_stretch(audio_data, rate=0.8)
```

### Concatenação
Múltiplos clipes de áudio podem ser unidos:

```python
# Assumindo que temos múltiplos arrays de áudio
combined_audio = np.concatenate([audio1, audio2, audio3])
```

## Análise de Ritmo e Tempo

### Detecção de Batidas
A LibROSA inclui funções para detectar batidas e onsets:

```python
# Detectar batidas
tempo, beat_frames = librosa.beat.beat_track(y=audio_data, sr=sr)
print(f"Tempo estimado: {tempo:.2f} BPM")

# Converter frames de batida para tempo
beat_times = librosa.frames_to_time(beat_frames, sr=sr)

# Visualizar batidas na forma de onda
plt.figure(figsize=(12, 4))
librosa.display.waveshow(audio_data, sr=sr)
plt.vlines(beat_times, -1, 1, color='red', alpha=0.6, label='Batidas')
plt.legend()
plt.title('Detecção de Batidas')
plt.show()
```

### Detecção de Onset
```python
# Detectar onsets (início de notas/eventos)
onset_frames = librosa.onset.onset_detect(y=audio_data, sr=sr)
onset_times = librosa.frames_to_time(onset_frames, sr=sr)

print(f"Número de onsets detectados: {len(onset_times)}")
```

### Análise de Tempogram
```python
# Calcular tempogram
tempo_gram = librosa.feature.tempogram(y=audio_data, sr=sr)

plt.figure(figsize=(12, 6))
librosa.display.specshow(tempo_gram, sr=sr, x_axis='time', y_axis='tempo')
plt.colorbar()
plt.title('Tempogram')
plt.show()
```

## Casos de Uso Avançados

### Separação de Fontes Harmônicas e Percussivas
```python
# Separar componentes harmônicos e percussivos
y_harmonic, y_percussive = librosa.effects.hpss(audio_data)

# Visualizar comparação
fig, axes = plt.subplots(3, 1, figsize=(12, 8))

# Original
axes[0].plot(librosa.frames_to_time(range(len(audio_data)), sr=sr), audio_data)
axes[0].set_title('Áudio Original')

# Harmônico
axes[1].plot(librosa.frames_to_time(range(len(y_harmonic)), sr=sr), y_harmonic)
axes[1].set_title('Componente Harmônico')

# Percussivo
axes[2].plot(librosa.frames_to_time(range(len(y_percussive)), sr=sr), y_percussive)
axes[2].set_title('Componente Percussivo')

plt.tight_layout()
plt.show()
```

### Análise de Fundamental (F0)
```python
# Extrair frequência fundamental
f0 = librosa.yin(audio_data, fmin=80, fmax=400, sr=sr)

# Remover valores não detectados (NaN)
times = librosa.frames_to_time(range(len(f0)), sr=sr)
f0_clean = f0[~np.isnan(f0)]
times_clean = times[~np.isnan(f0)]

plt.figure(figsize=(12, 4))
plt.plot(times_clean, f0_clean)
plt.xlabel('Tempo (s)')
plt.ylabel('Frequência (Hz)')
plt.title('Frequência Fundamental (F0)')
plt.show()
```

### Análise de Similaridade
```python
# Calcular matriz de similaridade baseada em croma
chroma = librosa.feature.chroma_cqt(y=audio_data, sr=sr)
similarity_matrix = np.corrcoef(chroma.T)

plt.figure(figsize=(10, 8))
plt.imshow(similarity_matrix, aspect='auto', origin='lower')
plt.colorbar()
plt.title('Matriz de Similaridade (Croma)')
plt.show()
```

## Integração com Machine Learning

### Preparação de Dados para ML
```python
def extract_features(audio_file):
    """Extrai múltiplas características de um arquivo de áudio"""
    y, sr = librosa.load(audio_file)
    
    # Extrair características
    mfcc = np.mean(librosa.feature.mfcc(y=y, sr=sr, n_mfcc=13).T, axis=0)
    chroma = np.mean(librosa.feature.chroma_stft(y=y, sr=sr).T, axis=0)
    contrast = np.mean(librosa.feature.spectral_contrast(y=y, sr=sr).T, axis=0)
    tonnetz = np.mean(librosa.feature.tonnetz(y=y, sr=sr).T, axis=0)
    
    # Características espectrais
    spectral_centroid = np.mean(librosa.feature.spectral_centroid(y=y, sr=sr))
    spectral_rolloff = np.mean(librosa.feature.spectral_rolloff(y=y, sr=sr))
    spectral_bandwidth = np.mean(librosa.feature.spectral_bandwidth(y=y, sr=sr))
    zcr = np.mean(librosa.feature.zero_crossing_rate(y))
    
    # Combinar todas as características
    features = np.concatenate([
        mfcc, chroma, contrast, tonnetz,
        [spectral_centroid, spectral_rolloff, spectral_bandwidth, zcr]
    ])
    
    return features

# Exemplo de uso
features = extract_features('audio_file.wav')
print(f"Vetor de características: {features.shape}")
```

### Exemplo com Scikit-learn
```python
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score

# Assumindo que temos uma lista de arquivos e labels
audio_files = ['file1.wav', 'file2.wav', ...]  # Lista de arquivos
labels = [0, 1, ...]  # Labels correspondentes

# Extrair características de todos os arquivos
X = np.array([extract_features(file) for file in audio_files])
y = np.array(labels)

# Dividir dados
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Treinar modelo
model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X_train, y_train)

# Avaliar
predictions = model.predict(X_test)
accuracy = accuracy_score(y_test, predictions)
print(f"Acurácia: {accuracy:.3f}")
```

## Melhores Práticas

### 1. Gerenciamento de Memória
```python
# Para arquivos grandes, processe em chunks
def process_large_audio(filename, chunk_duration=30.0):
    """Processa áudio grande em pedaços"""
    y, sr = librosa.load(filename, sr=None)
    duration = librosa.get_duration(y=y, sr=sr)
    
    results = []
    for start in range(0, int(duration), int(chunk_duration)):
        end = min(start + chunk_duration, duration)
        start_sample = int(start * sr)
        end_sample = int(end * sr)
        
        chunk = y[start_sample:end_sample]
        # Processar chunk
        features = extract_features_from_chunk(chunk, sr)
        results.append(features)
    
    return results
```

### 2. Normalização de Áudio
```python
# Normalizar áudio para prevenir problemas numéricos
def normalize_audio(y):
    """Normaliza áudio para range [-1, 1]"""
    return librosa.util.normalize(y)

# Aplicar
audio_normalized = normalize_audio(audio_data)
```

### 3. Padronização de Taxa de Amostragem
```python
# Sempre definir uma taxa de amostragem padrão
STANDARD_SR = 22050

def load_standardized(filename):
    """Carrega áudio com taxa de amostragem padrão"""
    return librosa.load(filename, sr=STANDARD_SR)
```

### 4. Tratamento de Erros
```python
def safe_feature_extraction(filename):
    """Extrai características com tratamento de erros"""
    try:
        y, sr = librosa.load(filename)
        
        if len(y) == 0:
            print(f"Arquivo vazio: {filename}")
            return None
            
        # Verificar se o áudio tem duração mínima
        if librosa.get_duration(y=y, sr=sr) < 1.0:
            print(f"Áudio muito curto: {filename}")
            return None
            
        # Extrair características
        features = extract_features_from_audio(y, sr)
        return features
        
    except Exception as e:
        print(f"Erro ao processar {filename}: {e}")
        return None
```

### 5. Configurações de Performance
```python
# Configurar para melhor performance
import multiprocessing

# Usar múltiplos cores para processamento em lote
def parallel_feature_extraction(file_list):
    """Extrai características em paralelo"""
    with multiprocessing.Pool() as pool:
        features = pool.map(safe_feature_extraction, file_list)
    return [f for f in features if f is not None]
```

## Conclusão

A LibROSA é uma biblioteca poderosa para análise e manipulação de áudio em Python. Ela pode ser utilizada para extrair características de arquivos de áudio, manipular áudio de várias maneiras e construir modelos de machine learning para tarefas como:

- Reconhecimento de fala
- Classificação de gêneros musicais  
- Reconhecimento de instrumentos
- Análise de sentimentos em áudio
- Sistemas de recomendação musical

Este tutorial cobriu os aspectos fundamentais da biblioteca, mas as capacidades discutidas aqui apenas arranham a superfície do que a LibROSA pode fazer. Para explorar funcionalidades mais avançadas, consulte a documentação oficial em https://librosa.org/doc/latest/.

### Recursos Adicionais

- [Documentação Oficial](https://librosa.org/doc/latest/)
- [Galeria de Exemplos](https://librosa.org/doc/latest/auto_examples/index.html)
- [Tutorial Avançado](https://librosa.org/doc/latest/advanced.html)
- [Repositório GitHub](https://github.com/librosa/librosa)

### Próximos Passos

1. Experimente com seus próprios arquivos de áudio
2. Explore diferentes combinações de características
3. Integre com frameworks de deep learning como TensorFlow ou PyTorch
4. Contribua para a comunidade LibROSA no GitHub
5. Participe de conferências e workshops sobre processamento de áudio