defmodule Talker do
  def loop do
    receive do
      {:greet, name} -> IO.puts("Hello #{name}")
      {:praise, name} -> IO.puts("Good job, #{name}")
      {:celebrate, name} -> IO.puts("Go back to work, #{name}")
      {:shutdown} -> exit(:normal)
    end

    loop
  end
end

Process.flag(:trap_exit, true)
pid = spawn(&Talker.loop/0)
send(pid, {:greet, 'Joe'})
send(pid, {:praise, 'Joe'})
send(pid, {:celebrate, 'Joe'})
send(pid, {:shutdown})

receive do
  {:EXIT, ^pid, reason} -> IO.puts("Talker has exited (#{reason})")
end
