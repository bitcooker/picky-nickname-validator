<script>
  let nickname = "";
  let state = "idle"; 
  // idle | loading | success | negotiation

  let message = "";
  let suggestions = [];

  async function submit(name) {
    if (!name || state === "loading") return;

    state = "loading";
    message = "";
    suggestions = [];

    try {
      const res = await fetch("http://localhost:8080/nickname", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nickname: name })
      });

      const data = await res.json();

      if (data.nickname) {
        state = "success";
        message = `Nice choice! Your nickname is set to “${data.nickname}”.`;
        nickname = "";
      } else {
        state = "negotiation";
        message = data.explanation;
        suggestions = data.suggestions;
      }
    } catch (e) {
      state = "idle";
      message = "Something went wrong. Please try again.";
    }
  }
</script>

<main>
  <h1>Picky Nickname Chooser</h1>

  <div class="card">
    <input
      placeholder="Try a nickname..."
      bind:value={nickname}
      on:keydown={(e) => e.key === 'Enter' && submit(nickname)}
      disabled={state === 'loading'}
    />

    <button on:click={() => submit(nickname)} disabled={state === 'loading'}>
      {state === 'loading' ? 'Thinking…' : 'Submit'}
    </button>

    {#if state === "loading"}
      <div class="loader" aria-label="Loading"></div>
      <p class="hint">Negotiating with the nickname engine…</p>
    {/if}

    {#if state === "success"}
      <p class="success">{message}</p>
    {/if}

    {#if state === "negotiation"}
      <p class="message">{message}</p>

      <div class="suggestions">
        {#each suggestions as s}
          <button class="suggestion" on:click={() => submit(s)}>
            {s}
          </button>
        {/each}
      </div>
    {/if}
  </div>
</main>

<style>
  main {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #1f2933, #111827);
    color: #e5e7eb;
    font-family: system-ui, sans-serif;
  }

  h1 {
    text-align: center;
    margin-bottom: 1.5rem;
    font-size: 1.75rem;
    margin-right: 1.5rem;
  }

  .card {
    background: #111827;
    padding: 2rem;
    border-radius: 12px;
    width: 360px;
    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.4);
  }

  input {
    width: 100%;
    padding: 0.75rem;
    border-radius: 8px;
    border: none;
    margin-bottom: 0.75rem;
    font-size: 1rem;
    box-sizing: border-box;
  }

  input:disabled {
    opacity: 0.6;
  }

  button {
    width: 100%;
    padding: 0.75rem;
    border-radius: 8px;
    border: none;
    background: #2563eb;
    color: white;
    font-size: 1rem;
    cursor: pointer;
  }

  button:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }

  .message {
    margin-top: 1rem;
    color: #d1d5db;
  }

  .success {
    margin-top: 1rem;
    color: #34d399;
  }

  .suggestions {
    margin-top: 1rem;
    display: grid;
    gap: 0.5rem;
  }

  .suggestion {
    background: #1f2933;
    border: 1px solid #374151;
  }

  .suggestion:hover {
    background: #374151;
  }

  /* Engine-style spinner */
  .loader {
    margin: 1.25rem auto 0.5rem;
    width: 32px;
    height: 32px;
    border: 4px solid #374151;
    border-top-color: #60a5fa;
    border-radius: 50%;
    animation: spin 0.9s linear infinite;
  }

  .hint {
    text-align: center;
    font-size: 0.85rem;
    color: #9ca3af;
  }

  @keyframes spin {
    to {
      transform: rotate(360deg);
    }
  }
</style>
