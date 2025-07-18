#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

sem_t mutex;      // Protects read_count
sem_t wrt;        // Writer semaphore

int read_count = 0;  // Number of readers currently accessing

void* reader(void* arg) {
    int id = *((int*)arg);

    sem_wait(&mutex);
    read_count++;
    if (read_count == 1)
        sem_wait(&wrt);  // First reader locks the writer
    sem_post(&mutex);

    // Reading section
    printf("Reader %d is reading...\n", id);
    sleep(1);
    printf("Reader %d finished reading.\n", id);

    sem_wait(&mutex);
    read_count--;
    if (read_count == 0)
        sem_post(&wrt);  // Last reader unlocks the writer
    sem_post(&mutex);

    return NULL;
}

void* writer(void* arg) {
    int id = *((int*)arg);

    sem_wait(&wrt);  // Writer locks access
    // Writing section
    printf("Writer %d is writing...\n", id);
    sleep(2);
    printf("Writer %d finished writing.\n", id);
    sem_post(&wrt);

    return NULL;
}

int main() {
    pthread_t r[5], w[2];
    int reader_ids[5] = {1, 2, 3, 4, 5};
    int writer_ids[2] = {1, 2};

    // Initialize semaphores
    sem_init(&mutex, 0, 1);
    sem_init(&wrt, 0, 1);

    // Create reader and writer threads
    for (int i = 0; i < 5; i++)
        pthread_create(&r[i], NULL, reader, &reader_ids[i]);

    for (int i = 0; i < 2; i++)
        pthread_create(&w[i], NULL, writer, &writer_ids[i]);

    // Wait for threads to finish
    for (int i = 0; i < 5; i++)
        pthread_join(r[i], NULL);

    for (int i = 0; i < 2; i++)
        pthread_join(w[i], NULL);

    // Destroy semaphores
    sem_destroy(&mutex);
    sem_destroy(&wrt);

    return 0;
}
