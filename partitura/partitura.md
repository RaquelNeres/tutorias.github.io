# Tutorial Completo da Biblioteca Partitura

## Índice
1. [Introdução](#introdução)
2. [Instalação](#instalação)
3. [Conceitos Fundamentais](#conceitos-fundamentais)
4. [Carregando Partituras](#carregando-partituras)
5. [Explorando a Estrutura de uma Partitura](#explorando-a-estrutura-de-uma-partitura)
6. [Trabalhando com Notas](#trabalhando-com-notas)
7. [Manipulação de Tempo Musical](#manipulação-de-tempo-musical)
8. [Visualização e Renderização](#visualização-e-renderização)
9. [Exportação e Salvamento](#exportação-e-salvamento)
10. [Análise Musical](#análise-musical)
11. [Trabalhando com Performance MIDI](#trabalhando-com-performance-midi)
12. [Casos de Uso Avançados](#casos-de-uso-avançados)
13. [Integração com Outras Bibliotecas](#integração-com-outras-bibliotecas)
14. [Melhores Práticas](#melhores-práticas)

## Introdução

Partitura é um pacote Python para manipular informações musicais simbólicas. Ele suporta carregamento e exportação para arquivos MusicXML e MIDI. Também suporta carregamento de Humdrum kern e MEI.

A Partitura é um pacote Python 3 para processamento de música simbólica desenvolvido e mantido na OFAI Vienna / CP JKU Linz. Ele foi projetado para fornecer uma representação musical leve que torna muitas propriedades de partitura facilmente acessíveis para uma variedade de tarefas.

### Principais Características

- **Suporte a múltiplos formatos**: MusicXML, MIDI, Kern, MEI
- **Representação estruturada**: Hierarquia clara de objetos musicais
- **Análise temporal**: Conversão entre diferentes unidades de tempo
- **Visualização**: Integração com Lilypond e MuseScore
- **Síntese de áudio**: Geração de arquivos WAV usando síntese aditiva
- **Processamento de performance**: Análise de dados MIDI de performance

### Aplicações

A biblioteca é especialmente útil para:
- Pesquisa em MIR (Music Information Retrieval)
- Análise musical computacional
- Processamento de partituras digitais
- Análise de performances musicais
- Educação musical assistida por computador

## Instalação

### Instalação Básica

A maneira mais fácil de instalar o pacote é via pip do PyPI (Python Package Index):

```bash
pip install partitura
```

### Instalação com Dependências Completas

Para funcionalidades avançadas como renderização visual:

```bash
pip install partitura[complete]
```

### Dependências Externas

Para renderização visual, você pode instalar:

**Lilypond** (recomendado):
```bash
# Ubuntu/Debian
sudo apt-get install lilypond

# macOS
brew install lilypond

# Windows: baixe do site oficial
```

**MuseScore** (alternativo):
```bash
# Instale pelo site oficial: https://musescore.org
```

### Verificando a Instalação

```python
import partitura as pt
print(f"Versão da Partitura: {pt.__version__}")

# Verificar arquivos de exemplo incluídos
print(f"MusicXML de exemplo: {pt.EXAMPLE_MUSICXML}")
print(f"MIDI de exemplo: {pt.EXAMPLE_MIDI}")
print(f"Kern de exemplo: {pt.EXAMPLE_KERN}")
```

## Conceitos Fundamentais

### Hierarquia de Objetos

A Partitura organiza informações musicais em uma hierarquia clara:

```
Score (Partitura)
├── Part (Parte/Instrumento)
│   ├── TimePoint (Ponto no Tempo)
│   │   ├── Note (Nota)
│   │   ├── Rest (Pausa)
│   │   ├── Measure (Compasso)
│   │   └── TimeSignature (Fórmula de Compasso)
│   └── BeatMap (Mapa de Batidas)
└── ScoreVariant (Variante de Performance)
```

### Unidades de Tempo

A Partitura trabalha com diferentes unidades de tempo:

- **Timeline Time (t)**: Unidade interna baseada em `divisions`
- **Beat Time**: Tempo em batidas/beats musicais
- **Seconds**: Tempo em segundos (para performances)
- **Quarters**: Tempo em quartos de nota

## Carregando Partituras

### Carregamento Genérico

A função load_score da partitura importará qualquer formato de partitura, ou seja, (Musicxml, Kern, MIDI ou MEI) para um objeto partitura.Score:

```python
import partitura as pt

# Carregamento genérico (detecta o formato automaticamente)
score = pt.load_score('caminho/para/arquivo.musicxml')
score = pt.load_score('caminho/para/arquivo.mid')
score = pt.load_score('caminho/para/arquivo.krn')
```

### Carregamento por Formato Específico

#### MusicXML

```python
# Carregar arquivo MusicXML
score = pt.load_musicxml('arquivo.musicxml')

# Usando exemplo incluído
example_xml = pt.EXAMPLE_MUSICXML
score = pt.load_musicxml(example_xml)
```

#### MIDI

```python
# Carregar arquivo MIDI
score = pt.load_midi('arquivo.mid')

# Especificar quantização (opcional)
score = pt.load_midi('arquivo.mid', quantization=4)
```

#### Kern

```python
# Carregar arquivo Kern
score = pt.load_kern('arquivo.krn')

# Usando exemplo incluído
example_kern = pt.EXAMPLE_KERN
score = pt.load_kern(example_kern)
```

#### MEI

```python
# Carregar arquivo MEI
score = pt.load_mei('arquivo.mei')

# Usando exemplo incluído
example_mei = pt.EXAMPLE_MEI
score = pt.load_mei(example_mei)
```

## Explorando a Estrutura de uma Partitura

### Informações Básicas da Partitura

```python
# Carregar exemplo
score = pt.load_score(pt.EXAMPLE_MUSICXML)

# Informações gerais
print(f"Número de partes: {len(score.parts)}")
print(f"Título: {score.title if hasattr(score, 'title') else 'N/A'}")

# Listar todas as partes
for i, part in enumerate(score.parts):
    print(f"Parte {i}: {part.part_name} (ID: {part.id})")
```

### Explorando uma Parte

O objeto score conterá todas as informações na partitura, incluindo as partes da partitura:

```python
# Selecionar a primeira parte
part = score.parts[0]

# Visualizar estrutura da parte
print(part.pretty())

# Informações básicas da parte
print(f"Nome da parte: {part.part_name}")
print(f"ID da parte: {part.id}")
print(f"Número de notas: {len(part.notes)}")
print(f"Número de pausas: {len(part.rests)}")
print(f"Número de compassos: {len(part.measures)}")
```

### TimePoints e Objetos Musicais

```python
# Acessar pontos no tempo
timepoints = part.timeline
print(f"Número de TimePoints: {len(timepoints)}")

# Examinar o primeiro TimePoint
first_tp = timepoints[0]
print(f"Tempo: {first_tp.t}")
print(f"Objetos iniciando: {len(first_tp.starting_objects)}")
print(f"Objetos terminando: {len(first_tp.ending_objects)}")

# Listar tipos de objetos em um TimePoint
for obj in first_tp.starting_objects:
    print(f"Tipo: {type(obj).__name__}, Descrição: {obj}")
```

## Trabalhando com Notas

### Acessando Notas

As notas nesta parte podem ser acessadas através da propriedade part.notes:

```python
# Obter todas as notas da parte
notes = part.notes
print(f"Total de notas: {len(notes)}")

# Examinar a primeira nota
first_note = notes[0]
print(f"Pitch MIDI: {first_note.midi_pitch}")
print(f"Nome da nota: {first_note.step}{first_note.octave}")
print(f"Alteração: {first_note.alter if first_note.alter else 'Natural'}")
print(f"Início: {first_note.start.t}")
print(f"Fim: {first_note.end.t}")
print(f"Duração: {first_note.duration_tied}")
```

### Propriedades das Notas

```python
# Explorar propriedades de uma nota
note = notes[0]

# Informações de altura
print(f"Altura MIDI: {note.midi_pitch}")
print(f"Nome da nota: {note.step}")
print(f"Oitava: {note.octave}")
print(f"Alteração: {note.alter}")

# Informações temporais
print(f"Tempo de início: {note.start.t}")
print(f"Tempo de fim: {note.end.t}")
print(f"Duração: {note.duration_tied}")

# Informações de notação
print(f"Tipo de nota: {note.type}")
print(f"Voz: {note.voice}")
print(f"Pauta: {note.staff}")

# Informações de articulação
print(f"Ligaduras: {note.tie_next or note.tie_prev}")
```

### Criando Piano Roll

O seguinte código armazena o início, fim e pitch midi das notas em um array numpy:

```python
import numpy as np

# Criar piano roll básico
pianoroll = np.array([(n.start.t, n.end.t, n.midi_pitch) for n in part.notes])
print("Piano Roll (início, fim, pitch MIDI):")
print(pianoroll)

# Versão expandida com mais informações
extended_pianoroll = np.array([
    (n.start.t, n.end.t, n.midi_pitch, n.voice, n.staff) 
    for n in part.notes
])
print("Piano Roll Estendido:")
print(extended_pianoroll)
```

### Filtragem de Notas

```python
# Filtrar notas por oitava
high_notes = [n for n in part.notes if n.octave >= 5]
print(f"Notas agudas (≥5ª oitava): {len(high_notes)}")

# Filtrar notas por duração
long_notes = [n for n in part.notes if n.duration_tied >= 24]  # meio compasso ou mais
print(f"Notas longas: {len(long_notes)}")

# Filtrar notas por voz
voice_1_notes = [n for n in part.notes if n.voice == 1]
print(f"Notas na voz 1: {len(voice_1_notes)}")

# Filtrar notas por range de pitch MIDI
middle_c_octave = [n for n in part.notes if 60 <= n.midi_pitch <= 71]  # C4 a B4
print(f"Notas entre C4 e B4: {len(middle_c_octave)}")
```

## Manipulação de Tempo Musical

### Beat Map

O objeto parte tem uma propriedade :part.beat_map que converte tempos de timeline em tempos de batida:

```python
# Obter o mapa de batidas
beat_map = part.beat_map

# Converter tempos de início das notas para batidas
start_times_timeline = pianoroll[:, 0]  # tempos em timeline
start_times_beats = beat_map(start_times_timeline)

print("Tempos de início:")
print("Timeline:", start_times_timeline)
print("Batidas:", start_times_beats)

# Converter tempos de fim
end_times_timeline = pianoroll[:, 1]
end_times_beats = beat_map(end_times_timeline)

print("Tempos de fim:")
print("Timeline:", end_times_timeline)
print("Batidas:", end_times_beats)
```

### Análise de Tempo

```python
# Obter informações de compasso e fórmula de compasso
time_signatures = part.time_signatures
print(f"Número de mudanças de compasso: {len(time_signatures)}")

for ts in time_signatures:
    print(f"Compasso {ts.numerator}/{ts.denominator} em t={ts.start.t}")

# Analisar duração total
total_duration_timeline = max([n.end.t for n in part.notes])
total_duration_beats = beat_map(total_duration_timeline)

print(f"Duração total: {total_duration_timeline} (timeline), {total_duration_beats} (beats)")
```

### Conversões de Tempo

```python
# Converter entre diferentes unidades de tempo
def timeline_to_quarters(timeline_time, beat_map, divisions=None):
    """Converte timeline time para quartos de nota"""
    beats = beat_map(timeline_time)
    return beats  # Em 4/4, beats = quarters

def seconds_to_timeline(seconds, tempo_bpm=120, divisions=12):
    """Converte segundos para timeline time (aproximado)"""
    beats_per_second = tempo_bpm / 60
    quarters_per_second = beats_per_second
    timeline_per_quarter = divisions
    return seconds * quarters_per_second * timeline_per_quarter

# Exemplo de uso
timeline_time = 24
beats = beat_map(timeline_time)
quarters = timeline_to_quarters(timeline_time, beat_map)

print(f"Timeline: {timeline_time}, Beats: {beats}, Quarters: {quarters}")
```

## Visualização e Renderização

### Renderização Básica

Se lilypond ou MuseScore estiverem instalados no sistema, o seguinte comando renderiza a parte para uma imagem e a exibe:

```python
# Renderizar parte completa (requer Lilypond ou MuseScore)
try:
    pt.render(part)
    print("Partitura renderizada com sucesso!")
except Exception as e:
    print(f"Erro na renderização: {e}")
    print("Certifique-se de ter Lilypond ou MuseScore instalado")
```

### Renderização com Opções

```python
# Renderização com parâmetros customizados
try:
    # Salvar como arquivo de imagem
    pt.render(part, out='minha_partitura.png', dpi=300)
    
    # Renderizar apenas um trecho (compassos 1-4)
    measures_subset = [m for m in part.measures if 1 <= m.number <= 4]
    if measures_subset:
        pt.render(part, out='trecho.png', measures=measures_subset)
        
except Exception as e:
    print(f"Renderização não disponível: {e}")
```

### Visualização de Análise

```python
import matplotlib.pyplot as plt

# Plotar piano roll
def plot_piano_roll(notes, title="Piano Roll"):
    fig, ax = plt.subplots(figsize=(12, 6))
    
    for note in notes:
        start_beat = beat_map(note.start.t)
        end_beat = beat_map(note.end.t)
        duration = end_beat - start_beat
        
        # Desenhar nota como retângulo
        rect = plt.Rectangle((start_beat, note.midi_pitch), 
                           duration, 0.8, 
                           facecolor='blue', alpha=0.7)
        ax.add_patch(rect)
    
    ax.set_xlabel('Tempo (beats)')
    ax.set_ylabel('Pitch MIDI')
    ax.set_title(title)
    ax.grid(True, alpha=0.3)
    
    # Configurar limites
    if notes:
        max_time = max(beat_map(n.end.t) for n in notes)
        min_pitch = min(n.midi_pitch for n in notes) - 1
        max_pitch = max(n.midi_pitch for n in notes) + 1
        
        ax.set_xlim(0, max_time)
        ax.set_ylim(min_pitch, max_pitch)
    
    plt.tight_layout()
    plt.show()

# Usar a função
plot_piano_roll(part.notes, f"Piano Roll - {part.part_name}")
```

### Visualização de Características Musicais

```python
# Plotar distribuição de alturas
def plot_pitch_distribution(notes):
    pitches = [n.midi_pitch for n in notes]
    
    plt.figure(figsize=(10, 6))
    plt.hist(pitches, bins=range(min(pitches), max(pitches) + 2), 
             alpha=0.7, edgecolor='black')
    plt.xlabel('Pitch MIDI')
    plt.ylabel('Frequência')
    plt.title('Distribuição de Alturas')
    plt.grid(True, alpha=0.3)
    plt.show()

# Plotar duração das notas
def plot_note_durations(notes, beat_map):
    durations = [beat_map(n.end.t) - beat_map(n.start.t) for n in notes]
    
    plt.figure(figsize=(10, 6))
    plt.hist(durations, bins=20, alpha=0.7, edgecolor='black')
    plt.xlabel('Duração (beats)')
    plt.ylabel('Frequência')
    plt.title('Distribuição de Durações das Notas')
    plt.grid(True, alpha=0.3)
    plt.show()

# Usar as funções
plot_pitch_distribution(part.notes)
plot_note_durations(part.notes, beat_map)
```

## Exportação e Salvamento

### Salvamento em MIDI

Os seguintes comandos salvam a parte em MIDI e MusicXML, ou a exportam como um arquivo WAV:

```python
# Salvar como MIDI
pt.save_score_midi(part, 'minha_partitura.mid')

# Salvar score completo como MIDI
pt.save_score_midi(score, 'partitura_completa.mid')

print("Arquivo MIDI salvo com sucesso!")
```

### Salvamento em MusicXML

```python
# Salvar como MusicXML
pt.save_musicxml(part, 'minha_partitura.musicxml')

# Salvar score completo
pt.save_musicxml(score, 'partitura_completa.musicxml')

print("Arquivo MusicXML salvo com sucesso!")
```

### Exportação como Áudio

```python
# Salvar como arquivo WAV usando síntese aditiva
pt.save_wav(part, 'minha_partitura.wav')

# Com parâmetros personalizados
pt.save_wav(part, 'partitura_custom.wav', 
           sample_rate=44100, 
           velocity=80)

print("Arquivo WAV salvo com sucesso!")
```

### Exportação Personalizada

```python
# Função para exportar dados como CSV
def export_to_csv(notes, beat_map, filename):
    import csv
    
    with open(filename, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(['start_beat', 'end_beat', 'midi_pitch', 
                        'note_name', 'octave', 'voice', 'staff'])
        
        for note in notes:
            start_beat = beat_map(note.start.t)
            end_beat = beat_map(note.end.t)
            note_name = f"{note.step}{note.alter if note.alter else ''}"
            
            writer.writerow([start_beat, end_beat, note.midi_pitch,
                           note_name, note.octave, note.voice, note.staff])
    
    print(f"Dados exportados para {filename}")

# Usar a função
export_to_csv(part.notes, beat_map, 'notas_exportadas.csv')
```

## Análise Musical

### Análise Intervalar

```python
# Calcular intervalos melódicos
def calculate_melodic_intervals(notes):
    intervals = []
    sorted_notes = sorted(notes, key=lambda n: n.start.t)
    
    for i in range(1, len(sorted_notes)):
        interval = sorted_notes[i].midi_pitch - sorted_notes[i-1].midi_pitch
        intervals.append(interval)
    
    return intervals

intervals = calculate_melodic_intervals(part.notes)
print(f"Intervalos melódicos: {intervals[:10]}...")  # Primeiros 10

# Análise estatística de intervalos
import numpy as np
if intervals:
    print(f"Intervalo médio: {np.mean(intervals):.2f} semitons")
    print(f"Desvio padrão: {np.std(intervals):.2f}")
    print(f"Intervalo máximo: {max(intervals)} semitons")
    print(f"Intervalo mínimo: {min(intervals)} semitons")
```

### Análise de Acordes

```python
# Detectar acordes (notas simultâneas)
def find_chords(notes, tolerance=0):
    chords = {}
    
    for note in notes:
        start_time = note.start.t
        
        # Agrupar notas que começam no mesmo tempo (±tolerance)
        chord_notes = [n for n in notes 
                      if abs(n.start.t - start_time) <= tolerance]
        
        if len(chord_notes) > 1:  # É um acorde
            chord_key = start_time
            if chord_key not in chords:
                chords[chord_key] = []
            chords[chord_key] = chord_notes
    
    return chords

chords = find_chords(part.notes)
print(f"Acordes encontrados: {len(chords)}")

for time, chord_notes in list(chords.items())[:5]:  # Primeiros 5 acordes
    pitches = [n.midi_pitch for n in chord_notes]
    beat_time = beat_map(time)
    print(f"Acorde no beat {beat_time}: {pitches}")
```

### Análise de Ritmo

```python
# Análise de padrões rítmicos
def analyze_rhythm_patterns(notes, beat_map):
    onset_times = [beat_map(n.start.t) for n in notes]
    onset_times.sort()
    
    # Calcular intervalos entre onsets
    inter_onset_intervals = []
    for i in range(1, len(onset_times)):
        interval = onset_times[i] - onset_times[i-1]
        inter_onset_intervals.append(interval)
    
    return inter_onset_intervals

rhythm_intervals = analyze_rhythm_patterns(part.notes, beat_map)
print(f"Intervalos rítmicos mais comuns:")

# Contar frequências dos intervalos
from collections import Counter
interval_counts = Counter(rhythm_intervals)
for interval, count in interval_counts.most_common(5):
    print(f"  {interval} beats: {count} vezes")
```

### Análise de Tessitura

```python
# Análise de range e tessitura
def analyze_tessitura(notes):
    if not notes:
        return None
    
    pitches = [n.midi_pitch for n in notes]
    
    analysis = {
        'min_pitch': min(pitches),
        'max_pitch': max(pitches),
        'range': max(pitches) - min(pitches),
        'mean_pitch': np.mean(pitches),
        'median_pitch': np.median(pitches),
        'std_pitch': np.std(pitches)
    }
    
    # Converter para nomes de notas
    def midi_to_note_name(midi_pitch):
        notes_names = ['C', 'C#', 'D', 'D#', 'E', 'F', 'F#', 'G', 'G#', 'A', 'A#', 'B']
        octave = (midi_pitch // 12) - 1
        note = notes_names[midi_pitch % 12]
        return f"{note}{octave}"
    
    analysis['min_note'] = midi_to_note_name(analysis['min_pitch'])
    analysis['max_note'] = midi_to_note_name(analysis['max_pitch'])
    
    return analysis

tessitura = analyze_tessitura(part.notes)
if tessitura:
    print("Análise de Tessitura:")
    print(f"  Range: {tessitura['min_note']} - {tessitura['max_note']}")
    print(f"  Amplitude: {tessitura['range']} semitons")
    print(f"  Altura média: {tessitura['mean_pitch']:.1f} ({midi_to_note_name(int(tessitura['mean_pitch']))})")
```

## Trabalhando com Performance MIDI

### Carregamento de Performance

```python
# Carregar performance MIDI com timing preciso
performance = pt.load_performance_midi('performance.mid')

# Comparar com partitura (se disponível)
if hasattr(performance, 'notes'):
    perf_notes = performance.notes
    print(f"Notas na performance: {len(perf_notes)}")
    
    # Analisar timing da performance
    onset_times = [n.start.t for n in perf_notes]
    print(f"Primeiro onset: {min(onset_times):.3f}s")
    print(f"Último onset: {max(onset_times):.3f}s")
```

### Análise de Performance

```python
# Análise de expressividade na performance
def analyze_performance_expression(notes):
    if not hasattr(notes[0], 'velocity'):
        print("Dados de velocity não disponíveis")
        return None
    
    velocities = [n.velocity for n in notes if hasattr(n, 'velocity')]
    durations = [(n.end.t - n.start.t) for n in notes]
    
    analysis = {
        'velocity_mean': np.mean(velocities),
        'velocity_std': np.std(velocities),
        'velocity_range': max(velocities) - min(velocities),
        'duration_mean': np.mean(durations),
        'duration_std': np.std(durations),
        'tempo_variation': np.std(np.diff([n.start.t for n in notes]))
    }
    
    return analysis

# Aplicar se dados de performance estiverem disponíveis
if 'performance' in locals():
    expr_analysis = analyze_performance_expression(perf_notes)
    if expr_analysis:
        print("Análise de Expressividade:")
        print(f"  Velocity média: {expr_analysis['velocity_mean']:.1f}")
        print(f"  Variação de velocity: {expr_analysis['velocity_std']:.1f}")
        print(f"  Variação de timing: {expr_analysis['tempo_variation']:.3f}")
```

### Comparação Partitura vs Performance

```python
# Função para comparar partitura com performance
def compare_score_performance(score_notes, perf_notes, tolerance=0.1):
    """Compara timing e pitches entre partitura e performance"""
    
    alignments = []
    
    for score_note in score_notes:
        # Encontrar nota correspondente na performance
        matching_perf_notes = [
            pn for pn in perf_notes 
            if (pn.midi_pitch == score_note.midi_pitch and
                abs(pn.start.t - score_note.start.t) < tolerance)
        ]
        
        if matching_perf_notes:
            perf_note = matching_perf_notes[0]  # Primeira correspondência
            timing_diff = perf_note.start.t - score_note.start.t
            duration_ratio = perf_note.duration_tied / score_note.duration_tied
            
            alignments.append({
                'score_onset': score_note.start.t,
                'perf_onset': perf_note.start.t,
                'timing_diff': timing_diff,
                'duration_ratio': duration_ratio,
                'pitch': score_note.midi_pitch
            })
    
    return alignments

# Usar se ambos datasets estiverem disponíveis
if 'performance' in locals() and 'part' in locals():
    alignments = compare_score_performance(part.notes, perf_notes)
    print(f"Notas alinhadas: {len(alignments)}")
    
    if alignments:
        timing_diffs = [a['timing_diff'] for a in alignments]
        duration_ratios = [a['duration_ratio'] for a in alignments]
        
        print(f"Desvio médio de timing: {np.mean(timing_diffs):.3f}s")
        print(f"Razão média de duração: {np.mean(duration_ratios):.3f}")