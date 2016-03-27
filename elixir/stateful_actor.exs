defmodule Counter do
  def loop(count) do
    receive do
      {:next} ->
        IO.puts("Current count: #{count}")
        loop(count + 1)
    end
  end
end

counter = spawn(Counter, :loop, [1])
send(:counter, {:next})
send(:counter, {:next})
