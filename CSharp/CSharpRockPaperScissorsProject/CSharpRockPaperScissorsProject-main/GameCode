using System;

namespace Rock_Paper_Scissors
{
    class Program
    {
        static void Main(string[] args)
        {
            Random random = new Random();
            int playerScore = 0;
            int computerScore = 0;

            Console.WriteLine("Welcome to Rock Paper Scissors Game!");

            while (playerScore < 3 && computerScore < 3)
            {
                Console.WriteLine("Player score = " + playerScore + ". Computer score = " + computerScore);
                Console.WriteLine("Please enter r for Rock, p for Paper, or s for Scissors");
                string playerChoice = Console.ReadLine().ToLower();
                int computerChoice = random.Next(0, 3);

                if (computerChoice == 0)
                {
                    Console.WriteLine("Computer chooses Rock.");
                    switch (playerChoice)
                    {
                        case "r":
                            Console.WriteLine("Tie!");
                            break;

                        case "p":
                            Console.WriteLine("Player wins this round!");
                            playerScore++;
                            break;

                        default:
                            Console.WriteLine("Computer wins this round!");
                            computerScore++;
                            break;
                    }
                }
                else if (computerChoice == 1)
                {
                    Console.WriteLine("Computer chooses Paper.");
                    switch (playerChoice)
                    {
                        case "r":
                            Console.WriteLine("Computer wins this round!");
                            computerScore++;
                            break;

                        case "p":
                            Console.WriteLine("Tie!");
                            break;

                        default:
                            Console.WriteLine("Player wins this round!");
                            playerScore++;
                            break;
                    }
                }
                else
                {
                    Console.WriteLine("Computer chooses Scissors.");
                    switch (playerChoice)
                    {
                        case "r":
                            Console.WriteLine("Player wins this round!");
                            playerScore++;
                            break;

                        case "p":
                            Console.WriteLine("Computer wins this round!");
                            computerScore++;
                            break;

                        default:
                            Console.WriteLine("Tie!");
                            break;
                    }
                }
            }
            if (playerScore == 3)
            {
                Console.WriteLine("Congratulations! YOU WIN!");
            }
            else
            {
                Console.WriteLine("Sorry, YOU LOSE!");
            }
        }
    }
}
