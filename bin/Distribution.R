# Create a sequence of numbers between 1 and 100 incrementing by 1.
x <- seq(1, 100, by = 1)
# Choose the mean as 2.5 and standard deviation as 0.5.
y <- dnorm(x, mean = 6.746888663585593, sd = 31.436306928462102)
print(y)
write.csv(y,"output.csv", row.names = FALSE)