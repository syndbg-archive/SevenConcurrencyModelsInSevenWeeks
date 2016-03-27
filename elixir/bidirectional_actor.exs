defmodule Counter do
  def start(count) do
    pid = spawn(__MODULE__, :loop, [count])
    Process.register(pid, :counter)
    pid
  end

  def next do
    ref = make_ref()

    send(:counter, {:next, self(), ref})

    receive do
      {:ok, ^ref, count} -> count
    end
  end

  def loop(count) do
    receive do
      {:next, sender, ref} ->
        send(sender, {:ok, ref, count})
        loop(count + 1)
    end
  end
end

counter = spawn(Counter, :loop, [1])
send(:counter, {:next})
send(:counter, {:next})
